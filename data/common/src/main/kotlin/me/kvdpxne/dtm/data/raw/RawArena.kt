package me.kvdpxne.dtm.data.raw

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
class RawArena(
  // @formatter:off
  val identifier       : String,
  val map              : RawArenaMap,
  val settings         : RawArenaSettings,
  val monumentPositions: Collection<RawMonumentPosition>,
  val revivalPositions : Collection<RawRevivalPosition>,
  val name             : String
  // @formatter:on
)