package me.kvdpxne.dtm.user

import java.util.Locale
import java.util.UUID
import me.kvdpxne.boujee.locale.BasicLocaleSource
import me.kvdpxne.boujee.locale.LocaleSource
import me.kvdpxne.boujee.locale.LocaleSourceProvider
import me.kvdpxne.boujee.locale.Locales
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.state.BasicMutableIdentifiable
import me.kvdpxne.dtm.user.statistics.UserStatistics
import me.kvdpxne.dtm.wallet.Wallet

open class BasicUser(
  // @formatter:off
  private val name                : String,
  private val displayName         : String?,
  private val statistics          : UserStatistics,
  private val wallet              : Wallet,
  private var currentProfession   : Profession,
  private var localeSource        : LocaleSource,
              initialModifiedState: Boolean       = true,
              identifier          : UUID          = UUID.randomUUID(),
  // @formatter:on
) :
  BasicMutableIdentifiable<UUID>(
    initialModifiedState,
    identifier
  ),
  User {

  override fun getName(): String {
    return this.name
  }

  override fun getDisplayName(): String? {
    return this.displayName
  }

  override fun getStatistics(): UserStatistics {
    return this.statistics
  }

  override fun getWallet(): Wallet {
    return this.wallet
  }

  @Synchronized
  override fun getCurrentProfession(): Profession {
    return this.currentProfession
  }

  @Synchronized
  override fun getLocaleSource(): LocaleSource {
    return this.localeSource
  }

  override fun updateLocaleSource(
    localeSourceProvider: LocaleSourceProvider
  ): Boolean {
    val localeSource: LocaleSource = localeSourceProvider.localeSource
    synchronized(this) {
      if (localeSource == this.localeSource) {
        return false
      }

      this.localeSource = localeSource
    }

    this.markAsModified()
    return true
  }

  override fun updateLocaleSource(
    localization: String
  ): Boolean {
    val locale: Locale = Locales.fromString(localization)
    val localeSource: LocaleSource = BasicLocaleSource(locale)

    synchronized(this) {
      if (localeSource == this.localeSource) {
        return false
      }

      this.localeSource = localeSource
    }

    this.markAsModified()
    return true
  }

  override fun getLocalUser(): LocalUser {
    return BasicLocalUser(
      this.name,
      this.displayName,
      this.statistics,
      this.wallet,
      this.currentProfession,
      this.localeSource,
      this.wasModified(),
      this.identifier,
    )
  }
}