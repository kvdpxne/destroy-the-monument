package me.kvdpxne.dtm.shared.basics.position

open class BaseBlockPosition(
  x: Int,
  y: Int,
  z: Int,
  worldName: String?
) : AbstractPosition<Int>(x, y, z, worldName), BlockPosition {

}