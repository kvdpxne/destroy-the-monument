package me.kvdpxne.dtm.configuration

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlNode
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.exists
import kotlin.io.path.readText
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.io.Files2

/**
 * @since 0.1.0
 */
object ConfigurationManager {

  /**
   * @since 0.1.0
   */
  private val knownConfigurations: Array<String> =
    arrayOf(
      "debug"
    )

  /**
   * @since 0.1.0
   */
  private val configurations: MutableMap<String, Configuration> =
    hashMapOf(
      "debug" to DebugConfiguration
    )

  /**
   * @since 0.1.0
   */
  fun moveConfigurations(target: Path) {
    Files2.fs(
      this::class.java.classLoader,
      "configurations2"
    ) { _, path ->
      Files.walk(path)
        .filter(Files::isRegularFile)
        .forEach {
          val f = target.resolve(it.fileName.toString())
          if (!f.exists()) {
            it.copyTo(f)
          }
        }
    }

    Debug.log {
      ""
    }
  }

  /**
   * @since 0.1.0
   */
  fun loadConfiguration(directory: Path, name: String) {
    val filePath: Path = directory.resolve("$name.yml")
    val content: String = filePath.readText()

    val node: YamlNode = Yaml.default.parseToYamlNode(content)

    this.configurations[name]?.loadConfiguration(node)
  }

  /**
   * @since 0.1.0
   */
  fun loadConfigurations(directory: Path) {
    for (configuration: String in this.knownConfigurations) {
      this.loadConfiguration(directory, configuration)
    }
  }
}