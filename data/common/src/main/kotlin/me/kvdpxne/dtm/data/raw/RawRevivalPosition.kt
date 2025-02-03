package me.kvdpxne.dtm.data.raw

class RawRevivalPosition(
  // @formatter:off
  val identifier: ByteArray,
  val team      : RawTeam,
  val x         : Double,
  val y         : Double,
  val z         : Double,
  val pitch     : Float,
  val yaw       : Float
  // @formatter:on
)
