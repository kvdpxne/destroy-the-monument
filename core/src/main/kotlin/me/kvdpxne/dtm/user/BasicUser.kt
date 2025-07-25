package me.kvdpxne.dtm.user

import java.util.Locale
import java.util.UUID
import java.util.function.Function
import me.kvdpxne.boujee.locale.BasicLocaleSource
import me.kvdpxne.boujee.locale.LocaleSource
import me.kvdpxne.boujee.locale.LocaleSourceProvider
import me.kvdpxne.boujee.locale.Locales
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionNoFoundException
import me.kvdpxne.dtm.profession.SingletonProfessionManager
import me.kvdpxne.dtm.shared.language.toSingleLines
import me.kvdpxne.dtm.state.BasicMutableIdentifiable
import me.kvdpxne.dtm.user.statistics.BasicUserStatistics
import me.kvdpxne.dtm.user.statistics.UserStatistics
import me.kvdpxne.dtm.wallet.BasicWallet
import me.kvdpxne.dtm.wallet.Wallet

/**
 * @param name
 * @param displayName
 * @param initialStatistics
 * @param initialWallet
 * @param
 *
 * @since 0.1.0
 */
open class BasicUser(
  // @formatter:off
  private val name                : String,
              initialStatistics   : UserStatistics?,
              initialWallet       : Wallet?,
  private var professionName      : String,
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

  /**
   * @since 0.1.0
   */
  private val statistics: UserStatistics by lazy {
    initialStatistics ?: BasicUserStatistics()
  }

  /**
   * @since 0.1.0
   */
  private val wallet: Wallet by lazy {
    initialWallet ?: BasicWallet()
  }

  /**
   * @since 0.1.0
   */
  protected var profession: Profession? = null

  override fun getName(): String {
    return this.name
  }

  override fun getStatistics(): UserStatistics {
    return this.statistics
  }

  override fun getWallet(): Wallet {
    return this.wallet
  }

  override fun getProfessionName(): String {
    return this.professionName
  }

  override fun getProfession(): Profession {
    var profession: Profession? = this.profession
    if (null != profession) {
      return profession
    }

    profession = SingletonProfessionManager.findProfessionByNameOrNull(
      // Przekazywana nazwa profesji zostanie automatycznie przekonwertowana na
      // małe znaki w ciele wywoływanej metody.
      this.professionName
    )

    if (null != profession) {
      this.profession = profession
      return profession
    }

    try {
      profession = SingletonProfessionManager.randomProfession
    } catch (_: NoSuchElementException) {
      throw ProfessionNoFoundException(
        """
          No profession can be found because the collection of registered
          professions is empty.
        """.toSingleLines(),
        ""
      )
    }

    this.professionName = profession.name
    this.profession = profession
    this.markAsModified()

    return profession
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

  override fun rebuild(): UserBuilder {
    return BasicUserBuilder(
      this.identifier,
      this.name,
      this.statistics,
      this.wallet,
      this.professionName
    )
  }

  override fun rebuild(
    mapper: Function<UserBuilder, out User>
  ): User {
    requireNotNull(mapper) {
      "Mapper function cannot be null"
    }

    val result: User? = mapper.apply(this.rebuild())
    requireNotNull(result) {
      "Mapper function must return non-null User instance"
    }

    return result
  }

  override fun getLocalUser(): LocalUser {
    return BasicLocalUser(
      this.name,
      this.statistics,
      this.wallet,
      this.professionName,
      this.localeSource,
      this.wasModified(),
      this.getIdentifier(),
    )
  }
}