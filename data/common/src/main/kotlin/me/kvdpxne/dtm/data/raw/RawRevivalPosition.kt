package me.kvdpxne.dtm.data.raw

import java.util.UUID

class RawRevivalPosition(
  // @formatter:off
  val identifier: UUID,
  val team      : RawTeam,
  val x         : Double,
  val y         : Double,
  val z         : Double,
  val pitch     : Float,
  val yaw       : Float
  // @formatter:on
)
