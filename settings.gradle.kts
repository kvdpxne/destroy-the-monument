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
      url = uri("https://jitpack.io")
      content {
        includeGroupAndSubgroups("com.github.kvdpxne")
      }
    }
  }

  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}

rootProject.name = "destroy-the-monument"