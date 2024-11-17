package me.kvdpxne.dtm.configuration

import com.charleskorn.kaml.YamlNode
import org.bukkit.Material

object GeneralConfiguration : Configuration {

  /**
   *
   */
  val USE_PROTOCOL_LIB = true && this.canUseProtocolLib()

  fun canUseProtocolLib(): Boolean {
    return try {
      Class.forName("com.comphenix.protocol.ProtocolLibrary")
      true
    } catch (_: ClassNotFoundException) {
      false
    }
  }

  const val BLOCK_ENCHANTING = true

  const val USER_MANAGER_INITIAL_CAPACITY = 24

  const val USE_FA_F = true

  val MONUMENT_TYPE = Material.OBSIDIAN

  const val TRACE_MESSAGES_IN_GAME = true

  const val TRACE_GLOBAL_MESSAGES_IN_GAME = true

  const val REVIVAL_PLAYER_DELAY = 22L

  const val GAME_END_DELAY = 20

  const val REVIVAL_PLAYER_PROTECTION_DELAY = 3

  const val MIN_TEAMMATES_SIZE = 2

  val FSFS = 30

  /**
   * @since 0.1.0
   */
  val EXTRA_SECONDS = 10

  var OP_F = true

  var BLOCK_PLAT_DROPS = true

  const val LOBBY_WORLD_NAME = "lobby"

  const val USE_PREFIX = true

  var OVERRIDE_DEFAULT_CHUNK_GENERATOR = true

  /**
   * @since 0.1.0
   */
  const val RADIUS_OF_BLOCK_INTERACTION = 3.874

  /**
   * @since 0.1.0
   */
  const val RADIUS_OF_EXPLOSION_INTERACTION = 11.941

  override val name: String
    get() = "general"

  override fun loadConfiguration(node: YamlNode) {
    TODO("Not yet implemented")
  }
}