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
  statistics          : UserStatistics?,
  wallet              : Wallet?,
  professionName      : String,
  localeSource        : LocaleSource,
  initialModifiedState: Boolean,
  identifier          : UUID
  // @formatter:on
) :
  BasicUser(
    name,
    statistics,
    wallet,
    professionName,
    localeSource,
    initialModifiedState,
    identifier
  ),
  LocalUser {

  /**
   * @since 0.1.0
   */
  private val cache: UserCache by lazy {
    BasicUserCache()
  }

  override fun getCache(): UserCache {
    return this.cache
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

  override fun setProfession(profession: Profession) {
    this.profession = profession
  }

  override fun getLocalUser(): LocalUser {
    return this
  }
}