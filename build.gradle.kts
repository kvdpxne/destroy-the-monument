import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  libraries.plugins.run {
    alias(kotlin)
    alias(shadow)
  }
}

description = "A simple game of destroying the monument of the opposing team."
group = "me.kvdpxne"
version = "0.1.0"

val targetJavaVersion = 8

// Filename with the extension.
val fileName = "craftbukkit-1.7.10.jar"

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
  compileOnly(files("libraries/$fileName"))

  implementation("org.xerial:sqlite-jdbc:3.42.0.0")

  implementation("org.ktorm:ktorm-support-sqlite:3.6.0")


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

  shadowJar {
    archiveClassifier.set("bukkit")
  }

  register("runMinecraftServer") {
    group = "minecraft"
    description = "Starts the minecraft server."

    val outputDirectory = file("run")
    val target = file("libraries/$fileName")

    doLast {
      outputDirectory.resolve(target.name).run {
        if (exists()) {
          return@doLast
        }

        target.copyTo(this, true)
      }
    }

    doLast {
      exec {
        workingDir = outputDirectory
        executable = "java"
        args("-jar", fileName)
        standardInput = System.`in`
      }
    }
  }

  register("buildAndMoveToRunDirectory") {
    group = "minecraft"
    description = "Builds the jar archive with shadowJar and move the built archive to the executable directory."

    dependsOn(shadowJar)

    val outputDirectory = file("run/plugins")
    val target = shadowJar.get().archiveFile.get().asFile

    doLast {
      target.copyTo(outputDirectory.resolve(target.name), true)
    }
  }
}