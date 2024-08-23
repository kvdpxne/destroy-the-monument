package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.basics.position.BaseIdentifiableEntityPosition
import me.kvdpxne.dtm.uid.Uid

/**
 * @param x
 * @param y
 * @param z
 * @param pitch
 * @param yaw
 * @param team
 * @param identifier
 */
class BaseRevivalPosition<T : Team>(
  // @formatter:off
               x         : Double,
               y         : Double,
               z         : Double,
               pitch     : Float,
               yaw       : Float,
  override var team      : T,
               identifier: String = Uid.next()
  // @formatter:on
) : BaseIdentifiableEntityPosition(
  x = x,
  y = y,
  z = z,
  pitch = pitch,
  yaw = yaw,
  worldName = null,
  identifier = identifier
),
  RevivalPosition<T> {

  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    if (!super.equals(other)) {
      return false
    }

    other as BaseRevivalPosition<*>
    return this.team == other.team
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + this.team.hashCode()
    return result
  }

  override fun toString(): String {
    return """
      RevivalPosition(
      x="${this.x}"
      y="${this.y}"
      z="${this.z}"
      pitch="${this.pitch}"
      yaw="${this.yaw}"
      team="${this.team}"
      identifier="${this.identifier}"
      )
    """.trimIndent()
  }
}