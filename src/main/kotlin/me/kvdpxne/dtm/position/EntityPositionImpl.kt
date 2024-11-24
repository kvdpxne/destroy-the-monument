package me.kvdpxne.dtm.position

class EntityPositionImpl(
  // @formatter:off
               x    : Double,
               y    : Double,
               z    : Double,
  override val pitch: Float,
  override val yaw  : Float,
               worldName: String?
  // @formatter:on
) : AbstractPosition<Double>(x, y, z, worldName), EntityPosition {

}