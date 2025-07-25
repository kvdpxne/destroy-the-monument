package me.kvdpxne.dtm.shared.specified

import me.kvdpxne.dtm.data.validation.rules.WalletRules
import me.kvdpxne.dtm.shared.randomBoolean
import me.kvdpxne.dtm.shared.randomFloat
import me.kvdpxne.dtm.shared.randomLong

/**
 * @since 0.1.0
 */
private typealias rwl = WalletRules

/**
 * @since 0.1.0
 */
fun randomCoins(): Long =
  randomLong(rwl.MIN_COINS, rwl.MAX_COINS)

/**
 * @since 0.1.0
 */
fun randomMultiplier(): Float =
  if (randomBoolean()) rwl.MIN_MULTIPLIER_INCLUSIVE_ZERO
  else randomFloat(rwl.MIN_MULTIPLIER, rwl.MAX_MULTIPLIER)