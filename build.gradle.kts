import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.TestLoggerPlugin
import com.adarshr.gradle.testlogger.theme.ThemeType
import org.gradle.model.internal.core.ModelNodes.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("java")
  id("maven-publish")

  libraries.plugins.run {
    alias(adarshr.testLogger).apply(false)
    alias(dokka).apply(false)
    alias(kotlin)
    alias(kotlin.serialization).apply(false)
    alias(shadow)
  }
}

allprojects {
  description = "A simple game of destroying the monument of the opposing team."
  group = "me.kvdpxne"
  version = "0.1.0"
}

subprojects {

  apply {
    plugin("java")
    plugin("maven-publish")

    plugin("com.adarshr.test-logger")
    plugin("org.jetbrains.kotlin.jvm")
    plugin("org.jetbrains.dokka")
    plugin("com.gradleup.shadow")
  }

//  plugins.withType<TestLoggerPlugin> {
//    configure<TestLoggerExtension> {
//      theme = ThemeType.STANDARD
//    }
//  }

  // The version of java used throughout the project.
  val targetJavaVersion = 8

  java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)

    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion

    if (JavaVersion.current() < javaVersion) {
      toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
  }

  dependencies {

  }

  tasks {

    withType<JavaCompile> {
      options.encoding = Charsets.UTF_8.name()

      if (10 <= targetJavaVersion || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
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

    withType<Test> {
      useJUnitPlatform()
    }

    withType<AbstractArchiveTask> {
      isPreserveFileTimestamps = false
      isReproducibleFileOrder = true
    }
  }
}

tasks {

  wrapper {
    distributionType = Wrapper.DistributionType.ALL
  }
}