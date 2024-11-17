package me.kvdpxne.dtm.configuration

import com.charleskorn.kaml.YamlNode

/**
 * @since 0.1.0
 */
object AdvancedConfiguration : Configuration {

  /**
   * @since 0.1.0
   */
  var ARENA_INITIAL_CAPACITY: Int = 24
    private set

  /**
   * @since 0.1.0
   */
  var GAME_INITIAL_CAPACITY: Int = 24
    private set

  /**
   * @since 0.1.0
   */
  var USER_INITIAL_CAPACITY: Int = 40
    private set

  override val name: String
    get() = "advanced"

  override fun loadConfiguration(node: YamlNode) {
    TODO("Not yet implemented")
  }
}