package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.ancillary.Buildable
import me.kvdpxne.dtm.wallet.Wallet
import me.kvdpxne.dtm.wallet.WalletImpl

/**
 * @param identifier
 * @param name
 * @param displayName
 *
 * @since 0.1.0
 */
class UserBuilder private constructor(
  // @formatter:off
  private val identifier : String,
  private val name       : String,
  private val displayName: String
  // @formatter:on
) : Buildable<User> {

  private var statistics: UserStatistics? = null
  private var wallet: Wallet? = null
  private var currentProfession: Profession? = null

  companion object {

    /**
     * @param identifier
     * @param name
     *
     * @since 0.1.0
     */
    fun create(
      identifier: String,
      name: String
    ): UserBuilder {
      require(identifier.isNotEmpty()) {
        "identifier must not be empty."
      }

      return UserBuilder(identifier, name.lowercase(), name)
    }
  }

  fun statistics(
    statistics: UserStatistics
  ): UserBuilder {
    this.statistics = statistics
    return this
  }

  fun wallet(
    wallet: WalletImpl
  ): UserBuilder {
    this.wallet = wallet
    return this
  }

  fun currentProfession(
    currentProfession: Profession
  ): UserBuilder {
    this.currentProfession = currentProfession
    return this
  }

  override fun build(): User {
    return UserImpl(
      this.name,
      this.displayName,
      this.statistics ?: UserStatisticsImpl(),
      this.wallet ?: WalletImpl(),
      this.currentProfession ?: ProfessionManager.randomProfession,
      this.identifier
    )
  }
}