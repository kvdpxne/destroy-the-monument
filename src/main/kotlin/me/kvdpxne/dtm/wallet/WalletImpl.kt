package me.kvdpxne.dtm.wallet

import kotlin.math.abs
import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid

/**
 * @param initialCoins
 * @param initialMultiplier
 * @param identifier
 *
 * @since 0.1.0
 */
class WalletImpl(
  // @formatter:off
  initialCoins     : Long   = 1000,
  initialMultiplier: Float  = 1.0F,
  identifier       : String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier), Wallet {

  /**
   * @since 0.1.0
   */
  private var _coins: Long = initialCoins

  /**
   * @since 0.1.0
   */
  private var _multiplier: Float = initialMultiplier

  /**
   * @since 0.1.0
   */
  override val coins: Long
    get() = this._coins

  /**
   * @since 0.1.0
   */
  override val multiplier: Float
    get() = this._multiplier

  /**
   * @since 0.1.0
   */
  override fun addCoins(
    coins: Long
  ) {
    this._coins += (coins * this._multiplier).toLong()
  }

  /**
   * @since 0.1.0
   */
  override fun subtractCoins(
    coins: Long
  ) {
    this._coins -= (coins * this._multiplier).toLong()
  }

  /**
   * @since 0.1.0
   */
  override fun updateCoins(
    coins: Long
  ) {
    this._coins = abs(coins)
  }

  /**
   * @since 0.1.0
   */
  override fun updateMultiplier(
    multiplier: Float
  ) {
    this._multiplier = abs(multiplier)
  }

  override fun toString(): String {
    return "Wallet{" +
      "coins=\"${this._coins}\", " +
      "multiplier=\"${this._multiplier}\"," +
      "identifier=\"${this.identifier}\"" +
      "}"
  }
}