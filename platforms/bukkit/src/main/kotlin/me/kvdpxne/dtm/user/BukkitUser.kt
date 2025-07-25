package me.kvdpxne.dtm.user

import me.kvdpxne.boujee.locale.LocaleSource
import me.kvdpxne.dtm.user.statistics.UserStatistics
import me.kvdpxne.dtm.wallet.Wallet

/**
 * @since 0.1.0
 */
class BukkitUser(
  // @formatter:off
  name             : String,
  displayName      : String?,
  initialStatistics: UserStatistics?,
  initialWallet    : Wallet?,
  professionName   : String,
  localeSource     : LocaleSource
  // @formatter:on
) :
  BasicUser(
    name,
    displayName,
    initialStatistics,
    initialWallet,
    professionName,
    localeSource
  ) {

  override fun getLocalUser(): LocalUser {
    return BukkitLocalUser(
      this.name,
      this.displayName,
      this.statistics,
      this.wallet,
      this.professionName,
      this.localeSource,
      this.wasModified(),
      this.getIdentifier()
    )
  }
}