package me.kvdpxne.dtm.data.raw

import java.util.UUID

/**
 * @param identifier
 * @param map
 * @param settings
 * @param monumentPositions
 * @param revivalPositions
 * @param name
 *
 * @since 0.1.0
 */
data class RawArena(
  // @formatter:off
  val identifier       : UUID,
  val map              : RawArenaMap,
  val settings         : RawArenaSettings,
  val monumentPositions: Collection<RawMonumentPosition>,
  val revivalPositions : Collection<RawRevivalPosition>,
  val name             : String
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}