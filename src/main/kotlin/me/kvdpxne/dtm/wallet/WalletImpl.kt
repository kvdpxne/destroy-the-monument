package me.kvdpxne.dtm.wallet

import java.util.UUID
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.shared.AbstractIdentifiable

/**
 * @param initialCoins
 * @param initialMultiplier
 * @param identifier
 *
 * @since 0.1.0
 */
class WalletImpl(
  // @formatter:off
  initialCoins     : Long  = 1000,
  initialMultiplier: Float = 1.0F,
  identifier       : UUID  = UUID.randomUUID(),
  // @formatter:on
) : AbstractIdentifiable<UUID>(identifier), Wallet {

  /**
   * @since 0.1.0
   */
  private var _coins: Long = initialCoins

  /**
   * @since 0.1.0
   */
  private var _multiplier: Float = initialMultiplier

  override var coins: Long
    get() = this._coins
    set(value) {
      require(value > 0) {
        "coins ($value) must be greater than 0"
      }

      this._coins = value
    }

  override var multiplier: Float
    get() = this._multiplier
    set(value) {
      require(value > 0) {
        "multiplier($value) must be greater than 0"
      }

      this._multiplier = value
    }

  /**
   * @param coins
   *
   */
  private fun calc(
    coins: Long
  ): Long {
    if (0.000f >= this._multiplier) {
      return coins
    }

    val value: Long = (coins * this._multiplier).toLong()
    if (0 > value) {
      throw ArithmeticException("long overflow")
    }

    return value
  }

  override fun addCoins(
    coins: Long
  ) {
    require(coins > 0) {
      "coins ($coins) must be greater than 0."
    }

    this._coins = Math.addExact(
      this._coins,
      this.calc(coins)
    )
  }

  override fun subtractCoins(
    coins: Long
  ) {
    require(coins > 0) {
      "coins ($coins) must be greater than 0."
    }

    this._coins = Math.subtractExact(
      this._coins,
      this.calc(coins)
    )
  }

  override fun compareTo(
    other: Wallet
  ): Int {
    return this.coins.compareTo(other.coins)
  }

  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("Wallet")
      .add("identifier", this.identifier)
      .add("coins", this._coins)
      .add("multiplier", this._multiplier)
      .build()
  }
}