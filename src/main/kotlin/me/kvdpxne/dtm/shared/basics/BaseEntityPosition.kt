package me.kvdpxne.dtm.shared.basics

class BaseEntityPosition(
  x: Double,
  y: Double,
  z: Double,
  override val pitch: Float,
  override val yaw: Float
) : AbstractPosition<Double>(x, y, z), EntityPosition {

  override fun isNear(
    x: Double,
    y: Double,
    z: Double,
    radius: Double
  ): Boolean {
    return this.x in x - radius..x + radius &&
      this.y in y - radius..y + radius &&
      this.z in z - radius..z + radius
  }
}