package me.kvdpxne.dtm.configuration

import org.bukkit.Material

object Configuration {

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

  const val USE_FA_F = true

  val MONUMENT_TYPE = Material.OBSIDIAN

  const val TRACE_MESSAGES_IN_GAME = true

  const val TRACE_GLOBAL_MESSAGES_IN_GAME = true

  const val REVIVAL_PLAYER_DELAY = 22L

  const val GAME_END_DELAY = 20

  const val REVIVAL_PLAYER_PROTECTION_DELAY = 3

  const val MIN_TEAMMATES_SIZE = 2

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

  const val NO_IN_GAME_MESSAGE = "&cBłąd: &7Nie jesteś grze."

  const val NO_ABILITY_MESSAGE = "&cBłąd: &7Twoja profesja nie posiada umiejętności specjalnej."

  const val NO_FOUND_ARENA = "&cBłąd&8: &7Arena o nazwie &c{ARENA_NAME} &7nie istnieje."

  const val NO_FOUND_GAME = "&cBłąd&8: &7Gra o nazwie &c{GAME_NAME} &7nie istnieje."

  const val NO_FOUND_TEAM = "&cBłąd&8: &7Drużyna o nazwie &c{TEAM_NAME} &7nie istnieje."

  const val SPAWN_BLOCK_BREAK_DENIED_MESSAGE = "&6&lDTM &7> &cNie możesz niszczyć bloków na spawnie."

  const val SPAWN_BLOCK_PLACEMENT_DENIED_MESSAGE = "&6&lDTM &7> &cNie możesz stawiać bloków na spawnie."

  const val BUILD_HEIGHT_LIMIT_MESSAGE = "&6&lDTM &7> &cOsiągnełeś możliwy limit budowania na tej mapie."

  const val FSF = "&6&lDTM &7> &fNie możesz zniszczyć monumentu swojej drużyny."

  val GAME_END_MESSAGE = arrayOf(
    "",
    "&6&lDTM &7> &fGra została zakończona.",
    "&6&lDTM &7> &fZa &6$GAME_END_DELAY &fsekund zostaniesz przeniesiony do poczekalni.",
  )
}