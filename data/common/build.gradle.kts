// The current version of the “data-common” module.
version = "0.1.0"

dependencies {
  testImplementation(kotlin("test"))

  shadow(project(":api"))
  implementation(project(":api"))
  api(project(":api"))

  implementation("com.github.kvdpxne.boujee:api:caa9aac6f0")
  implementation("com.github.kvdpxne.boujee:core:caa9aac6f0")

  implementation(libraries.f4b6a3.uuid.creator)
  implementation(libraries.bundles.exposed)
}