package me.kvdpxne.dtm.user.extensions

import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.wallet.BasicWallet
import me.kvdpxne.dtm.wallet.Wallet

/**
 * @since 0.1.0
 */
fun RawUserWallet.toUserWallet(): Wallet {
  return BasicWallet(
    // @formatter:off
    identifier           = this.identifier,
    initialCoins         = this.coins,
    initialMultiplier    = this.multiplier,
    // Początkowy stan modyfikacji obiektu w tym przypadku powinien być zawsze
    // ustawiony na fałsz, ponieważ dane pobrane z zewnętrznego źródła nie
    // zostały jeszcze zmodyfikowane (uaktualnienie nie jest potrzebne).
    initialModifiedState = false
    // @formatter:on
  )
}