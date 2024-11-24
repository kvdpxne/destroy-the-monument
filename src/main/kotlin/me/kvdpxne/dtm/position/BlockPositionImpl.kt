package me.kvdpxne.dtm.position

open class BlockPositionImpl(
  x: Int,
  y: Int,
  z: Int,
  worldName: String?
) : AbstractPosition<Int>(x, y, z, worldName), BlockPosition {

}