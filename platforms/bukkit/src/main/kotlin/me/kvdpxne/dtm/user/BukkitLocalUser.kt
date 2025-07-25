package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.boujee.TranslationKeyProvider
import me.kvdpxne.boujee.locale.LocaleSource
import me.kvdpxne.dtm.user.contractor.BukkitUserContractor
import me.kvdpxne.dtm.user.contractor.UserContractor
import me.kvdpxne.dtm.user.statistics.UserStatistics
import me.kvdpxne.dtm.wallet.Wallet

class BukkitLocalUser(
  // @formatter:off
  name                : String,
  displayName         : String?,
  statistics          : UserStatistics?,
  wallet              : Wallet?,
  professionName      : String,
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
    professionName,
    localeSource,
    initialModifiedState,
    identifier
  ) {

  /**
   * @since 0.1.0
   */
  private val contractor: UserContractor by lazy {
    BukkitUserContractor(this)
  }

  override fun getContractor(): UserContractor {
    return this.contractor
  }

  override fun chat(keyProvider: TranslationKeyProvider) {
    this.contractor.chat(keyProvider)
  }

  override fun title(keyProvider: TranslationKeyProvider) {
    this.contractor.title(keyProvider)
  }

  override fun subtitle(keyProvider: TranslationKeyProvider) {
    this.contractor.subtitle(keyProvider)
  }

  override fun action(keyProvider: TranslationKeyProvider) {
    this.contractor.action(keyProvider)
  }
}