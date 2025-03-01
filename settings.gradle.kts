pluginManagement {

  repositories {
    gradlePluginPortal()

    mavenCentral()
    mavenLocal()
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {

  versionCatalogs {
    val fileName = "libraries"
    create(fileName) {
      from(files("gradle/$fileName.versions.toml"))
    }
  }

  repositories {
    mavenCentral()
    mavenLocal()

    maven {
      url = uri("https://jitpack.io/")
//      content {
//        includeGroupAndSubgroups("com.github.kvdpxne")
//      }
    }

    maven("https://oss.sonatype.org/content/repositories/snapshots")

    // Maven repositories containing older versions of spigot-api, such as
    // 1.7.x, 1.6.x and 1.5.x, and all newer versions of spigot-api from
    // 1.8 upwards.
    maven("https://repo.md-5.net/content/repositories/public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")

    maven("https://repo.dmulloy2.net/repository/public/")
  }

  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}

arrayOf(
  "api",
  "commands:api",
  "commands:content",
  "common",
  "configurations",
  "core",
  "data:common",
  "data:database",
  "data:fs",
  "platforms:bukkit"
).forEach {
  val name = it.replace(':', '-')
  val directory = file("./${it.replace(':', '/')}")

  include(name)
  project(":$name").projectDir = directory
}

rootProject.name = "destroy-the-monument"