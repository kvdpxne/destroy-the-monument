package me.kvdpxne.dtm.shared.basics.position

import java.util.UUID

open class BaseIdentifiableBlockPosition(
  x: Int,
  y: Int,
  z: Int,
  worldName: String?,
  identifier: UUID
) : AbstractIdentifiablePosition<Int>(x, y, z, worldName, identifier), BlockPosition {

}