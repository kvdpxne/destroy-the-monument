package me.kvdpxne.dtm.shared.basics.position

import java.util.UUID

open class BaseIdentifiableEntityPosition(
  // @formatter:off
               x         : Double,
               y         : Double,
               z         : Double,
  override val pitch     : Float,
  override val yaw       : Float,
               worldName : String?,
               identifier: UUID
  // @formatter:on
) : AbstractIdentifiablePosition<Double>(x, y, z, worldName, identifier), EntityPosition