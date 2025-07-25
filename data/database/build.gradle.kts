dependencies {
//  project(":data-common").let {
//    api(it)
//    testFixturesImplementation(it)
//  }

  implementation(project(":api"))
  testImplementation(testFixtures(project(":data-common")))
//  testFixturesImplementation(project(":data-common"))

  implementation(libraries.exposed.core)
  implementation(libraries.exposed.jdbc)

  implementation(
    // The project, depending on user needs, is compiled in different versions
    // of Java and the HikariCP dependency version 6.x.x (the latest) requires
    // Java version 11 or higher, compatible with version 11.
    if (java.targetCompatibility.isJava11Compatible) {
      libraries.hikaricp.v6
    } else {
      // Deprecated
      libraries.hikaricp.v4
    }
  )

  implementation("org.jetbrains.exposed:exposed-migration:0.59.0")

  runtimeOnly(libraries.postgresql)
  runtimeOnly(libraries.sqlite)

  implementation(libraries.f4b6a3.uuid.creator)
  implementation(project(":data-common"))

  testImplementation(kotlin("test"))
  testImplementation("org.slf4j:slf4j-simple:2.1.0-alpha1")
}