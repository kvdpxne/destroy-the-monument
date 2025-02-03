package me.kvdpxne.dtm.data.raw.extensions

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.wallet.Wallet

/**
 * @since 0.1.0
 */
fun RawUserWallet.Companion.of(
  // @formatter:off
  identifier: UUID,
  coins     : Long,
  multiplier: Float,
  infinite  : Boolean,
  locked    : Boolean
  // @formatter:on
): RawUserWallet {
  require(0 <= coins) {
    "The number of coins cannot be negative."
  }

  require(multiplier in 0.01F..1.0F) {
    "The number of multipliers cannot be negative."
  }

  return RawUserWallet(
    identifier,
    coins,
    multiplier,
    infinite,
    locked
  )
}

/**
 * @since 0.1.0
 */
fun Wallet.toRawUserWallet(): RawUserWallet {
  return RawUserWallet.of(
    this.identifier,
    this.coins,
    this.multiplier,
    this.isInfinity,
    this.isBlocked
  )
}