plugins {
    kotlin("jvm")
}

group = "me.kvdpxne"
version = "0.1.0"

dependencies {
  implementation(project(":api"))
  implementation("com.github.kvdpxne.boujee:api:caa9aac6f0")
  implementation("com.github.kvdpxne.boujee:core:caa9aac6f0")

  implementation(libraries.exposed.core)
  implementation(libraries.exposed.jdbc)

  runtimeOnly(libraries.postgresql)
  runtimeOnly(libraries.sqlite)

  implementation(libraries.f4b6a3.uuid.creator)
  implementation(project(":data-common"))

  testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}