package me.kvdpxne.dtm.damage

import java.util.UUID
import me.kvdpxne.dtm.shared.StylishToStringBuilder

/**
 * Represents the damage inflicted by a specific attacker, along with metadata
 * about the damage amount and the time of last interaction.
 *
 * @param attacker The user who inflicted the damage.
 * @param damages The initial amount of damage inflicted.
 * @param lastInteractTime The last time the attacker dealt damage or the
 *                         damage value changed, defaulting to the current time.
 *
 * @since 0.1.0
 */
class Damage internal constructor(
  // @formatter:off
  val attacker        : UUID,
      damages         : Double,
      lastInteractTime: Long = System.currentTimeMillis()
  // @formatter:on
) {

  init {
    require(0 < damages) {
      "Damage value must be greater than zero!"
    }

    require(System.currentTimeMillis() >= lastInteractTime) {
      "Last interaction time cannot be in the future."
    }
  }

  /**
   * The amount of damage inflicted, which can be updated internally.
   *
   * @since 0.1.0
   */
  var damages: Double = damages
    internal set(value) {
      field = value
      this.lastInteractTime = System.currentTimeMillis()
    }

  /**
   * The last time the attacker dealt damage or the damages value changed.
   *
   * @since 0.1.0
   */
  var lastInteractTime: Long = lastInteractTime
    internal set

  /**
   * Compares this [Damage] instance to another object based on the attacker.
   *
   * @param other The other object to compare.
   * @return `true` if the other object is of the same class and has the same
   *         attacker; `false` otherwise.
   *
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

    other as Damage
    return this.attacker == other.attacker
  }

  /**
   * Computes a hash code based on the attacker for efficient data
   * structure usage.
   *
   * @return The hash code of this Damage instance.
   *
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.attacker.hashCode()
  }

  /**
   * Generates a string representation of this [Damage] instance for debugging
   * and logging purposes, using a custom builder.
   *
   * @return A stylized string representing the damage details.
   *
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder().begin("damage")
      .add("attacker", this.attacker)
      .add("damages", this.damages)
      .add("lastInteractTime", this.lastInteractTime)
      .build()
  }
}