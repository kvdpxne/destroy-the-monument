package me.kvdpxne.dtm.data.validation.context.extensions

import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.validation.context.isUserWalletValid

/**
 * @since 0.1.0
 */
fun RawUserWallet?.validate(): Int {
  return isUserWalletValid(this)
}