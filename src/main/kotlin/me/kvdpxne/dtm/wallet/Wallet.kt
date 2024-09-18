package me.kvdpxne.dtm.wallet

import me.kvdpxne.dtm.shared.ancillary.Identifiable

/**
 * @since 0.1.0
 */
interface Wallet : Identifiable<String> {

  /**
   * @since 0.1.0
   */
  override val identifier: String

  /**
   * @since 0.1.0
   */
  val coins: Long

  /**
   * @since 0.1.0
   */
  val multiplier: Float

  /**
   * @since 0.1.0
   */
  fun addCoins(coins: Long)

  /**
   * @since 0.1.0
   */
  fun subtractCoins(coins: Long)

  /**
   * @since 0.1.0
   */
  fun updateCoins(coins: Long)

  /**
   * @since 0.1.0
   */
  fun updateMultiplier(multiplier: Float)
}