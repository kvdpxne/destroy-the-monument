package me.kvdpxne.dtm.statistics

import me.kvdpxne.dtm.uid.Uid

/**
 * A concrete base class for representing basic player statistics with a unique
 * identifier.
 *
 * This class inherits from both `BaseStatistics` and `IdentifiableStatistics`.
 * It provides basic player statistics (kills, assists, deaths, destroyed
 * monuments) and a unique string identifier generated using
 * `UUID.randomUUID().toString()`. It also defines methods for checking equality
 * and calculating a hash code based on the identifier.
 *
 * Subclasses of `BaseIdentifiableStatistics` can potentially add more
 * specific statistics or override methods from either base class.
 *
 * @param kills The initial number of kills (default 0).
 * @param assists The initial number of assists (default 0).
 * @param deaths The initial number of deaths (default 0).
 * @param destroyedMonuments The initial number of destroyed monuments (default 0).
 * @param identifier A unique string identifier (defaults to a random UUID).
 *
 * @since 0.1.0
 */
open class BaseIdentifiableStatistics(
  // @formatter:off
   kills             : Int    = 0,
   assists           : Int    = 0,
   deaths            : Int    = 0,
   destroyedMonuments: Int    = 0,
   identifier        : String = Uid.next()
  // @formatter:on
) : BaseStatistics(kills, assists, deaths, destroyedMonuments),
  IdentifiableStatistics {

  /**
   * The unique string identifier associated with this player statistic.
   *
   * This value is inherited from the constructor and cannot be changed after
   * object creation.
   *
   * @since 0.1.0
   */
  @Suppress("CanBePrimaryConstructorProperty")
  override val identifier: String = identifier

  /**
   * Indicates whether this object is equal to another object.
   *
   * Two `BaseIdentifiableStatistics` objects are considered equal if they have
   * the same identifier.
   *
   * @param other The object to compare with.
   * @return True if the objects are equal, false otherwise.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (this.javaClass != other?.javaClass) return false

    other as BaseIdentifiableStatistics

    return this.identifier == other.identifier
  }

  /**
   * Returns a hash code value for this object.
   *
   * The hash code is based on the identifier's hash code.
   *
   * @return The hash code value of this object.
   */
  override fun hashCode(): Int {
    return this.identifier.hashCode()
  }
}