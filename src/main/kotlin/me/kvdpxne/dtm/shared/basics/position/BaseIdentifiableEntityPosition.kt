package me.kvdpxne.dtm.shared.basics.position

open class BaseIdentifiableEntityPosition(
  // @formatter:off
               x         : Double,
               y         : Double,
               z         : Double,
  override val pitch     : Float,
  override val yaw       : Float,
               worldName: String?,
               identifier: String
  // @formatter:on
) : AbstractIdentifiablePosition<Double>(x, y, z, worldName, identifier), EntityPosition {

}