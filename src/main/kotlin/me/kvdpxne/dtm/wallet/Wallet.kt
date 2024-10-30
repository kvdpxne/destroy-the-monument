package me.kvdpxne.dtm.wallet

import java.util.UUID
import me.kvdpxne.dtm.shared.Identifiable

/**
 * @since 0.1.0
 */
interface Wallet : Comparable<Wallet>, Identifiable<UUID> {

  /**
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  var coins: Long

  /**
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  var multiplier: Float

  /**
   * @throws IllegalArgumentException
   * @throws ArithmeticException
   *
   * @since 0.1.0
   */
  fun addCoins(coins: Long)

  /**
   * @throws IllegalArgumentException
   * @throws ArithmeticException
   *
   * @since 0.1.0
   */
  fun subtractCoins(coins: Long)
}