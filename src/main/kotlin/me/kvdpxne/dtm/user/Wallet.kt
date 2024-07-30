package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid

/**
 * @param initialCoins
 * @param initialMultiplier
 * @param identifier
 */
class Wallet(
  // @formatter:off
  initialCoins     : Long   = 1000,
  initialMultiplier: Float  = 1.0F,
  identifier       : String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier) {

  /**
   * @since 0.1.0
   */
  var coins: Long = initialCoins
    set(value) {
      require(0 <= value) {
        "Coins must be 0 <= $value"
      }

      field = value
    }

  /**
   * @since 0.1.0
   */
  var multiplier: Float = initialMultiplier
    set(value) {
      require(1.0F <= value) {
        "Multiplier must be 1 <= $value"
      }

      field = value
    }

  /**
   * @since 0.1.0
   */
  fun addCoins(value: Int) {
    this.coins += (value * this.multiplier).toInt()
  }

  /**
   * @since 0.1.0
   */
  fun subtractCoins(value: Int) {
    this.coins -= (value * this.multiplier).toInt()
  }
}