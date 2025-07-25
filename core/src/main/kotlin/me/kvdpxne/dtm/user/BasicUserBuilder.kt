package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.translation.SingletonTranslationService
import me.kvdpxne.dtm.user.statistics.UserStatistics
import me.kvdpxne.dtm.wallet.Wallet

class BasicUserBuilder(
  private var identifier: UUID? = null,
  private var name: String? = null,
  private var statistics: UserStatistics? = null,
  private var wallet: Wallet? = null,
  private var profession: String? = null,
) : UserBuilder {

  override fun setIdentifier(identifier: UUID): UserBuilder {
    this.identifier = identifier
    return this
  }

  override fun setName(name: String): UserBuilder? {
    this.name = name
    return this
  }

  override fun setStatistics(statistics: UserStatistics): UserBuilder? {
    this.statistics = statistics
    return this
  }

  override fun setWallet(wallet: Wallet): UserBuilder? {
    this.wallet = wallet
    return this
  }

  override fun setProfession(profession: String): UserBuilder? {
    this.profession = profession
    return this
  }


  override fun build(): User {
    val identifier = requireNotNull(this@BasicUserBuilder.identifier) { "Identifier must not be null!" }
    val name = requireNotNull(name) { "Name must not be null!" }
    val stats = requireNotNull(statistics) { "Statistics must not be null!" }
    val wallet = requireNotNull(wallet) { "Wallet must not be null!" }
    val profession = requireNotNull(profession) { "Profession must not be null!" }

    return BasicUser(
      identifier = this.identifier!!,
      name = this.name!!,
      initialStatistics = this.statistics!!,
      initialWallet = this.wallet!!,
      professionName = this.profession!!,
      localeSource = SingletonTranslationService.defaultLocaleSource,
    )


  }
}