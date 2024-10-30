package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.Buildable
import me.kvdpxne.dtm.wallet.Wallet
import me.kvdpxne.dtm.wallet.WalletImpl
import org.bukkit.entity.Player

/**
 * @param identifier
 * @param name
 * @param displayName
 *
 * @since 0.1.0
 */
class UserBuilder private constructor(
  // @formatter:off
  private val identifier : UUID,
  private val name       : String,
  private val displayName: String
  // @formatter:on
) : Buildable<User> {

  // @formatter:off
  private var statistics       : UserStatistics? = null
  private var wallet           : Wallet?         = null
  private var currentProfession: Profession?     = null
  // @formatter:on

  companion object {

    /**
     * @param identifier
     * @param name
     *
     * @since 0.1.0
     */
    fun create(
      identifier: UUID,
      name: String
    ): UserBuilder {
      return UserBuilder(identifier, name.lowercase(), name)
    }

    /**
     * @param player
     *
     * @since 0.1.0
     */
    fun create(
      player: Player
    ): UserBuilder {
      return this.create(player.uniqueId, player.name)
    }
  }

  fun statistics(
    statistics: UserStatistics
  ): UserBuilder {
    this.statistics = statistics
    return this
  }

  fun wallet(
    wallet: Wallet
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