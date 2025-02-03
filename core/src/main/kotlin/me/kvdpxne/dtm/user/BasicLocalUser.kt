package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.boujee.TranslationKeyProvider
import me.kvdpxne.boujee.locale.LocaleSource
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.user.cache.BasicUserCache
import me.kvdpxne.dtm.user.cache.UserCache
import me.kvdpxne.dtm.user.contractor.UserContractor
import me.kvdpxne.dtm.user.statistics.UserStatistics
import me.kvdpxne.dtm.wallet.Wallet

open class BasicLocalUser(
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
  BasicUser(
    name,
    displayName,
    statistics,
    wallet,
    currentProfession,
    localeSource,
    initialModifiedState,
    identifier
  ),
  LocalUser {

  /**
   * @since 0.1.0
   */
  private val cacheDelegate: UserCache by lazy {
    BasicUserCache()
  }

  override fun getCache(): UserCache {
    return this.cacheDelegate
  }

  override fun getContractor(): UserContractor {
    TODO("Not yet implemented")
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

  override fun setCurrentProfession(profession: Profession) {
    TODO("Not yet implemented")
  }

  override fun getLocalUser(): LocalUser {
    return this
  }
}