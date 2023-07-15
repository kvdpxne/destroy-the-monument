import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libraries.plugins.kotlin)
}

description = "A simple game of destroying the monument of the opposing team."
group = "me.kvdpxne"
version = "0.1.0"

val targetJavaVersion = 8

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
  compileOnly(files("./libraries/craftbukkit-1.7.10.jar"))
  testImplementation(kotlin("test"))
}

java {
  val javaVersion = JavaVersion.toVersion(targetJavaVersion)
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
  if (JavaVersion.current() < javaVersion) {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
  }
}

tasks {

  wrapper {
    distributionType = Wrapper.DistributionType.ALL
  }

  withType<JavaCompile> {
    if (10 <= targetJavaVersion || JavaVersion.current().isJava10Compatible) {
      options.release = targetJavaVersion
    }
  }

  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
  }

  processResources {
    val properties = mapOf(
      "description" to rootProject.description,
      "version" to rootProject.version
    )

    inputs.properties(properties)
    filteringCharset = "UTF-8"

    filesMatching(listOf("plugin.yaml", "plugin.yml")) {
      expand(properties)
    }
  }

  test {
    useJUnitPlatform()
  }

  register("runMinecraftServer") {
    group = "minecraft"

    // Filename with the extension.
    val fileName = "craftbukkit-1.7.10.jar"

    val destinationDirectory = file("run")
    val sourceFile = file("./libraries/$fileName")

//    doLast {
//      if (sourceFile.exists() && sourceFile.isFile) {
//        destinationDirectory.mkdirs()
//
//        sourceFile.copyTo(destinationDirectory, overwrite = true)
//      }
//    }

    doLast {
      exec {
        workingDir = destinationDirectory
        executable = "java"
        args("-jar", fileName)
        standardInput = System.`in`
      }
    }
  }

  val fatJar = register<Jar>("fatJar") {
    dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
    archiveClassifier.set("standalone") // Naming the jar
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val sourcesMain = sourceSets.main.get()
    val contents = configurations.runtimeClasspath.get()
      .map { if (it.isDirectory) it else zipTree(it) } + sourcesMain.output
    from(contents)
  }

  build {
    dependsOn(fatJar)
  }
}