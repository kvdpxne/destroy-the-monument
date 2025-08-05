import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  libraries.plugins.run {
    alias(dokka)
    alias(kotlin)
    alias(kotlin.serialization)
    alias(shadow)
  }
}

description = "A simple game of destroying the monument of the opposing team."
group = "me.kvdpxne"
version = "0.1.0"

configurations.all {
  resolutionStrategy.dependencySubstitution {
    // The version of the spigot api that the ProtocolLib plugin uses.
    val version = "1.7.10-R0.1-SNAPSHOT"

    substitute(module("org.spigotmc:spigot:$version"))
      .using(module("org.spigotmc:spigot-api:$version"))
      .because("The artifact named spigot has been replaced by spigot-api.")
  }
}

val targetJavaVersion = 8

// Filename with the extension.
val fileName = "spigot-1.7.10-SNAPSHOT-b1657.jar"

dependencies {
  try {
    shadow(files("run/$fileName"))
    shadow(files("run/cache/patched_1.8.8.jar"))
  } catch (_: Exception) {
    shadow(libraries.spigot.legacy)
  }

  implementation("org.jetbrains:annotations:26.0.2")

  implementation(libraries.bundles.exposed)
  implementation(libraries.kotlinx.serialization.json)
  implementation(libraries.kotlinx.serialization.kaml)

  implementation(libraries.postgresql)

  implementation("fr.mrmicky:fastboard:2.1.2")

  implementation(libraries.dico)
  implementation(libraries.notchity)

  shadow(libraries.protocollib.current)
//  shadow(libraries.protocollib.legacy)

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

    compilerOptions {
      jvmTarget.set(JvmTarget.fromTarget(value))
    }
  }

  processResources {
    //
    dependsOn("processConfigurations", "processTranslations")

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

  withType<Test> {
    useJUnitPlatform()
  }

  withType<AbstractArchiveTask> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
  }

  shadowJar {
    archiveClassifier.set("bukkit")
  }

  register("processConfigurations") {
    description = "Copies to resources configuration files."

    doLast {
      val source = layout.projectDirectory.dir("configurations2/").asFile
      val target = layout.buildDirectory.dir("resources/main/configurations2/").get().asFile

      if (!target.exists()) {
        target.mkdirs()
      }

      source.walkTopDown()
        .filter { it.isFile && it.extension == "yml" }
        .forEach {
          it.copyTo(File(target, it.name), overwrite = true)
        }
    }
  }

  register("processTranslations") {
    description = "Copies to resources and removes extra spaces from translation files."

    doLast {
      val source = layout.projectDirectory.dir("translations/").asFile
      val target = layout.buildDirectory.dir("resources/main/translations/").get().asFile

      if (!target.exists()) {
        target.mkdirs()
      }

      source.walkTopDown()
        .filter { it.isFile && it.extension == "json" }
        .forEach {
          it.copyTo(File(target, it.name), overwrite = true)
        }
    }
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
        args("-jar", "bukkit-1.8.8.jar")
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