package me.kvdpxne.dtm.wallet

import java.util.UUID
import me.kvdpxne.dtm.data.state.MutableState
import me.kvdpxne.dtm.shared.Identifiable

/**
 * Represents a wallet that holds a balance of coins and allows for
 * modification of its value. A wallet can have a multiplier applied to it,
 * affecting the value of coins added or subtracted.
 *
 * The wallet is identifiable by a unique [UUID] and provides state tracking
 * through [MutableState].
 *
 * @since 0.1.0
 */
interface Wallet : Comparable<Wallet>, Identifiable<UUID>, MutableState {

  /**
   * The current number of coins held in the wallet. This value can be modified
   * through [addCoins] and [subtractCoins].
   *
   * @throws IllegalArgumentException if a negative value is assigned.
   * @since 0.1.0
   */
  var coins: Long

  /**
   * A multiplier that affects the value of coins added to or subtracted from
   * the wallet. The multiplier must be a positive value.
   *
   * @throws IllegalArgumentException if a non-positive multiplier is assigned.
   * @since 0.1.0
   */
  var multiplier: Float

  /**
   * Adds the specified amount of [coins] to the wallet, factoring in the
   * [multiplier]. The effective addition is calculated as `coins * multiplier`.
   *
   * @param coins The number of coins to add, before applying the multiplier.
   * @throws IllegalArgumentException if `coins` is negative.
   * @throws ArithmeticException if the addition results in an overflow.
   * @since 0.1.0
   */
  fun addCoins(coins: Long)

  /**
   * Subtracts the specified amount of [coins] from the wallet, factoring in
   * the [multiplier]. The effective subtraction is calculated as
   * `coins * multiplier`.
   *
   * @param coins The number of coins to subtract, before applying the
   *              multiplier.
   * @throws IllegalArgumentException if `coins` is negative.
   * @throws ArithmeticException if the subtraction results in an underflow.
   * @since 0.1.0
   */
  fun subtractCoins(coins: Long)
}