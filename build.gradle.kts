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

val targetJavaVersion = 11

// Filename with the extension.
val fileName = "spigot-1.7.10-SNAPSHOT-b1657.jar"

dependencies {
  try {
    compileOnly(files("run/$fileName"))
  } catch (_: Exception) {
    compileOnly(libraries.spigot.legacy)
  }

  implementation(libraries.bundles.exposed)
  implementation(libraries.postgresql)

//  implementation(libraries.thrivi)
  implementation("fr.mrmicky:fastboard:2.1.2")

  implementation(libraries.bundles.disco)
  implementation(libraries.notchity)

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
    var value: String = targetJavaVersion.toString()
    if (8 >= targetJavaVersion) {
      value = "1.$value"
    }

    kotlinOptions.jvmTarget = value
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

    val outputDirectory = file("run/plugins/update")
    val target = shadowJar.get().archiveFile.get().asFile

    doLast {
      target.copyTo(outputDirectory.resolve(target.name), true)
    }
  }
}