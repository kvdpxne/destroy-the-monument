package me.kvdpxne.dtm.shared.basics

import me.kvdpxne.dtm.uid.Uid

open class BaseIdentifiableBlockPosition(
  x: Int,
  y: Int,
  z: Int,
  identifier: String = Uid.next()
) : AbstractIdentifiablePosition<Int>(x, y, z, identifier), BlockPosition {

  override fun isNear(
    x: Int,
    y: Int,
    z: Int,
    radius: Int
  ): Boolean {
    return this.x in x - radius..x + radius &&
      this.y in y - radius..y + radius &&
      this.z in z - radius..z + radius
  }

  override fun isIn(
    x: Int,
    y: Int,
    z: Int
  ): Boolean {
    return this.x == x && this.y == y && this.z == z
  }
}