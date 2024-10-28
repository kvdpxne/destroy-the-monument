package me.kvdpxne.dtm.position

import java.util.UUID
import me.kvdpxne.dtm.team.Team

/**
 * @param x
 * @param y
 * @param z
 * @param team
 * @param identifier
 *
 * @since 0.1.0
 */
class MonumentPositionImpl<T : Team>(
  // @formatter:off
               x         : Int,
               y         : Int,
               z         : Int,
  override val team      : T,
               identifier: UUID = UUID.randomUUID()
  // @formatter:on
) : BaseIdentifiableBlockPosition(
  x = x,
  y = y,
  z = z,
  worldName = null,
  identifier = identifier
),
  Cloneable,
  MonumentPosition<T> {

  /**
   * @since 0.1.0
   */
  override var isDestroyed: Boolean = false
    private set

  /**
   * @since 0.1.0
   */
  override fun restore() {
    this.isDestroyed = false
  }

  /**
   * @since 0.1.0
   */
  override fun destroy() {
    this.isDestroyed = true
  }

  /**
   * @since 0.1.0
   */
  override fun clone(): MonumentPosition<T> {
    return MonumentPositionImpl(
      this.x,
      this.y,
      this.z,
      this.team,
      this.identifier
    )
  }

  /**
   * @since 0.1.0
   */
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

    other as MonumentPosition<*>
    return this.team == other.team
  }

  /**
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + this.team.hashCode()
    return result
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return "MonumentPosition{" +
      "x=\"${this.x}\", " +
      "y=\"${this.y}\", " +
      "z=\"${this.z}\", " +
      "team=\"${this.team}\", " +
      "identifier=\"${this.identifier}\"" +
      "}"
  }
}