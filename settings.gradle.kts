pluginManagement {

  repositories {
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {

  versionCatalogs {
    val fileName = "libraries"
    create(fileName) {
      from(files("./gradle/${fileName}.versions.toml"))
    }
  }
}

rootProject.name = "destroy-the-monument"