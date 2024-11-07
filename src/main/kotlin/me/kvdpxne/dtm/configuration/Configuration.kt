package me.kvdpxne.dtm.configuration

import com.charleskorn.kaml.YamlNode

/**
 * @since 0.1.0
 */
interface Configuration {

  /**
   * @since 0.1.0
   */
  val name: String

  /**
   * @since 0.1.0
   */
  fun loadConfiguration(node: YamlNode)
}