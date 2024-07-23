package me.kvdpxne.dtm.wallet

import java.util.UUID

/**
 * @param initialCoins
 * @param initialMultiplier
 * @param identifier
 */
class Wallet(
  // @formatter:off
      initialCoins     : Int    = 1000,
      initialMultiplier: Float  = 1.0F,
  val identifier       : String = UUID.randomUUID().toString(),
  // @formatter:on
) {

  /**
   *
   */
  var coins: Int = initialCoins
    set(value) {
      require(0 <= value) {
        "Coins must be 0 <= $value"
      }

      field = value
    }

  /**
   *
   */
  var multiplier: Float = initialMultiplier
    set(value) {
      require(1.0F <= value) {
        "Multiplier must be 1 <= $value"
      }

      field = value
    }

  /**
   *
   */
  fun addCoins(value: Int) {
    this.coins += (value * this.multiplier).toInt()
  }

  /**
   *
   */
  fun subtractCoins(value: Int) {
    this.coins -= (value * this.multiplier).toInt()
  }
}