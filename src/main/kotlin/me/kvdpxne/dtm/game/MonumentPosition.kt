package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.basics.position.BaseIdentifiableBlockPosition
import me.kvdpxne.dtm.uid.Uid

/**
 * @param x
 * @param y
 * @param z
 * @param team
 * @param identifier
 *
 * @since 0.1.0
 */
class MonumentPosition(
  // @formatter:off
      x         : Int,
      y         : Int,
      z         : Int,
  val team      : TeamIdentity,
      identifier: String = Uid.next()
  // @formatter:on
) : BaseIdentifiableBlockPosition(x, y, z, null, identifier), Cloneable {

  /**
   * @since 0.1.0
   */
  var isDestroyed: Boolean = false
    private set

  /**
   * @since 0.1.0
   */
  fun restore() {
    this.isDestroyed = false
  }

  /**
   * @since 0.1.0
   */
  fun destroy() {
    this.isDestroyed = true
  }

  /**
   * @since 0.1.0
   */
  override fun clone(): MonumentPosition {
    return MonumentPosition(
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

    other as MonumentPosition
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
    return """
      MonumentPosition(
      x="${this.x}"
      y="${this.y}"
      z="${this.z}"
      team="${this.team}"
      identifier="${this.identifier}"
      )
    """.trimIndent()
  }


}