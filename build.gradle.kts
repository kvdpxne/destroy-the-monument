plugins {
  alias(libraries.plugins.kotlin)
}

description = "A simple game of destroying the monument of the opposing team."
group = "me.kvdpxne"
version = "0.1.0"

val targetJavaVersion = 8

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
  compileOnly(files("./libraries/craftbukkit-1.7.10.jar"))
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
}