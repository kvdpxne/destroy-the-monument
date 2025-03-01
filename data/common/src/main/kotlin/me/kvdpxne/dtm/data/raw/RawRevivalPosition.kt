package me.kvdpxne.dtm.data.raw

import java.util.UUID

/**
 * @since 0.1.0
 */
data class RawRevivalPosition(
  // @formatter:off
  val identifier: UUID,
  val team      : RawTeam,
  val x         : Double,
  val y         : Double,
  val z         : Double,
  val pitch     : Float,
  val yaw       : Float
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  companion object
}
