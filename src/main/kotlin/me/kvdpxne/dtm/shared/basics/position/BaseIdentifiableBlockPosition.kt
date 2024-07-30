package me.kvdpxne.dtm.shared.basics.position

open class BaseIdentifiableBlockPosition(
  x: Int,
  y: Int,
  z: Int,
  worldName: String?,
  identifier: String
) : AbstractIdentifiablePosition<Int>(x, y, z, worldName, identifier), BlockPosition {

}