package me.kvdpxne.dtm.position

open class BaseBlockPosition(
  x: Int,
  y: Int,
  z: Int,
  worldName: String?
) : AbstractPosition<Int>(x, y, z, worldName), BlockPosition {

}