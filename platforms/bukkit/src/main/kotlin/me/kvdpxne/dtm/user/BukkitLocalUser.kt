package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.boujee.locale.LocaleSource
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.user.contractor.BukkitUserContractor
import me.kvdpxne.dtm.user.contractor.UserContractor
import me.kvdpxne.dtm.user.statistics.UserStatistics
import me.kvdpxne.dtm.wallet.Wallet

class BukkitLocalUser(
  // @formatter:off
  name                : String,
  displayName         : String?,
  statistics          : UserStatistics,
  wallet              : Wallet,
  currentProfession   : Profession,
  localeSource        : LocaleSource,
  initialModifiedState: Boolean,
  identifier          : UUID
  // @formatter:on
) :
  BasicLocalUser(
    name,
    displayName,
    statistics,
    wallet,
    currentProfession,
    localeSource,
    initialModifiedState,
    identifier
  ) {

  private val lazyContractor: UserContractor by lazy {
    BukkitUserContractor(this)
  }

  override fun getContractor(): UserContractor {
    return this.lazyContractor
  }
}