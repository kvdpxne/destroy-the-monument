package me.kvdpxne.dtm.shared.basics.position

interface EntityPosition : Position<Double> {

  val pitch: Float

  val yaw: Float

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