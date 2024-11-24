package me.kvdpxne.dtm.position

import java.util.UUID

open class IdentifiableBlockPositionImpl(
  x: Int,
  y: Int,
  z: Int,
  worldName: String?,
  identifier: UUID
) : AbstractIdentifiablePosition<Int>(x, y, z, worldName, identifier), BlockPosition {

}