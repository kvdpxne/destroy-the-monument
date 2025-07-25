version = "0.1.0"

dependencies {
  api(project(":api"))
  api(project(":data-common"))
  api(project(":data-database"))

  implementation("com.github.kvdpxne.boujee:api:caa9aac6f0")
  implementation("com.github.kvdpxne.boujee:core:caa9aac6f0")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

  implementation(libraries.jetbrains.annotations)
  implementation(libraries.f4b6a3.uuid.creator)
}
