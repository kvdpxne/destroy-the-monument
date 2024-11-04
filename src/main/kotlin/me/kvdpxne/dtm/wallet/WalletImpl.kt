package me.kvdpxne.dtm.wallet

import java.util.UUID
import me.kvdpxne.dtm.data.state.BaseIdentifiableMutableState
import me.kvdpxne.dtm.shared.StylishToStringBuilder

/**
 * Implementation of the [Wallet] interface, representing a wallet with a
 * modifiable balance of coins and a multiplier that affects transactions.
 *
 * This class provides methods for adding and subtracting coins, as well as for
 * tracking changes to its state.
 *
 * @param initialCoins The initial amount of coins in the wallet. Must be
 *                     positive. Defaults to 1000.
 * @param initialMultiplier The initial multiplier applied to transactions.
 *                          Must be greater than 0. Defaults to 1.0.
 * @param identifier A unique identifier for the wallet, used for identification
 *                   purposes. Defaults to a randomly generated UUID.
 *
 * @since 0.1.0
 */
class WalletImpl(
  // @formatter:off
  initialCoins     : Long  = 1000,
  initialMultiplier: Float = 1.0F,
  identifier       : UUID  = UUID.randomUUID(),
  // @formatter:on
) : BaseIdentifiableMutableState<UUID>(identifier), Wallet {

  /**
   * Backing field for the [coins] property, initially set to [initialCoins].
   *
   * This value is modified only via `coins`, `addCoins`, and `subtractCoins`.
   *
   * @since 0.1.0
   */
  private var _coins: Long = initialCoins

  /**
   * Backing field for the [multiplier] property, initially set to
   * [initialMultiplier].
   *
   * Only positive values are permitted, and updates are tracked.
   *
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
      this.markAsModified()
    }

  override var multiplier: Float
    get() = this._multiplier
    set(value) {
      require(value > 0) {
        "multiplier($value) must be greater than 0"
      }

      this._multiplier = value
      this.markAsModified()
    }

  /**
   * Calculates the effective amount of coins based on the [multiplier].
   * If the multiplier is zero, returns the input coins unchanged.
   *
   * @param coins The base amount of coins before applying the multiplier.
   * @return The coins amount after applying the multiplier.
   * @throws ArithmeticException if the result overflows a `Long` value.
   *
   * @since 0.1.0
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

  /**
   * Compares this wallet to another wallet based on the number of coins.
   *
   * @param other The wallet to compare with.
   * @return A negative integer, zero, or a positive integer if this wallet has
   *         fewer, equal, or more coins than the specified wallet.
   *
   * @since 0.1.0
   */
  override fun compareTo(
    other: Wallet
  ): Int {
    return this.coins.compareTo(other.coins)
  }

  /**
   * Generates a string representation of the wallet, including the identifier,
   * coins, and multiplier, using the [StylishToStringBuilder].
   *
   * @return A formatted string representation of the wallet.
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("Wallet")
      .add("identifier", this.identifier)
      .add("coins", this._coins)
      .add("multiplier", this._multiplier)
      .build()
  }
}