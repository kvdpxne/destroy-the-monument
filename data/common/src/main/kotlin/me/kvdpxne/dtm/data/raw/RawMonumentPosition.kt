package me.kvdpxne.dtm.data.raw

import java.util.UUID

/**
 * @since 0.1.0
 */
data class RawMonumentPosition(
  // @formatter:off
  val identifier: UUID,
  val team      : RawTeam,
  val x         : Int,
  val y         : Int,
  val z         : Int
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}
