package me.kvdpxne.dtm.data.raw.extensions

import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.user.User

fun User.toRawUser(): RawUser {
  val trimmedName: String = this.name.trim()
  require(trimmedName.length in 3..16) {
    ""
  }

  val lowerName: String = trimmedName.lowercase()
  val lowerProfessionName: String = this.currentProfession.name.lowercase()
  val lowerLocalization: String = this.localeSource.localization.lowercase()

  return RawUser(
    this.identifier,
    this.statistics.toRawUserStatistics(),
    this.wallet.toRawUserWallet(),
    lowerName,
    this.displayName,
    lowerProfessionName,
    lowerLocalization,
  )
}