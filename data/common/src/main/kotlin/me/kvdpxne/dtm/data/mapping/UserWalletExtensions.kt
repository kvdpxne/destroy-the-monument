package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.shared.mapNotNullTo
import me.kvdpxne.dtm.wallet.Wallet

/**
 * Converts a [Wallet] domain object to its raw data representation [RawUserWallet].
 *
 * @return [RawUserWallet] instance with equivalent properties
 * @since 0.1.0
 */
fun Wallet.toRawUserWallet(): RawUserWallet {
  return RawUserWallet(
    this.identifier,
    this.coins,
    this.multiplier,
    this.isInfinity,
    this.isBlocked
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<Wallet?>.toRawUserWallets(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawUserWallet> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    Wallet::toRawUserWallet
  )
}