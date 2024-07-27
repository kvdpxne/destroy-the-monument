package me.kvdpxne.dtm.shared.basics

import me.kvdpxne.dtm.uid.Uid

open class BaseIdentifiableEntityPosition(
  // @formatter:off
               x         : Double,
               y         : Double,
               z         : Double,
  override val pitch     : Float,
  override val yaw       : Float,
               identifier: String = Uid.next()
  // @formatter:on
) : AbstractIdentifiablePosition<Double>(x, y, z, identifier), EntityPosition {

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