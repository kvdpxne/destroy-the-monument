package me.kvdpxne.dtm.configuration

import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import me.kvdpxne.dtm.shared.debug.Debug

/**
 * @since 0.1.0
 */
object DebugConfiguration : Configuration {

  override val name: String
    get() = "debug"

  /**
   * @since 0.1.0
   */
  var consolePrintOutput: Boolean = false
    private set

  /**
   * @since 0.1.0
   */
  var gamePrintOutput: Boolean = false
    private set

  /**
   * @since 0.1.0
   */
  override fun loadConfiguration(node: YamlNode) {
    val map: YamlMap = node.yamlMap

    this.consolePrintOutput = map.getScalar("consolePrintOutput")!!.toBoolean()
    this.gamePrintOutput = map.getScalar("consolePrintOutput")!!.toBoolean()

    Debug.log {
      "The ${this.name} configuration file has been loaded."
    }
  }
}