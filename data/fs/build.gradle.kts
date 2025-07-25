dependencies {
  api(project(":data-common"))

  implementation(libraries.kotlinx.serialization.cbor)
  testFixturesImplementation(project(":data-common"))
}