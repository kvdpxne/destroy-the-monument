package me.kvdpxne.dtm.data.raw

import java.util.UUID

/**
 * @since 0.1.0
 */
data class RawGame(
  // @formatter:off
  val identifier : UUID,
  val arenas     : Collection<RawArena>,
  val teams      : Collection<RawTeam>,
  val name       : String
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}