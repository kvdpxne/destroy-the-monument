package me.kvdpxne.dtm.user

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.wallet.Wallet

/**
 * Implementation of [LocalUser], representing a user with extended in-game
 * capabilities such as team association, performance management, and messaging.
 *
 * This class builds upon [UserImpl] by adding game-specific and local caching
 * functionality.
 *
 * @param name The unique name of the user.
 * @param displayName The display name shown in the game interface.
 * @param statistics The user's statistics record, tracking performance and
 *                   other metrics.
 * @param wallet The wallet associated with the user, managing in-game currency
 *               and transactions.
 * @param currentProfession The profession of the user, defining their abilities
 *                          and role in the game.
 * @param locale
 * @param identifier The unique identifier of the user, typically a [UUID].
 *
 * @since 0.1.0
 */
class LocalUserImpl(
  // @formatter:off
  name             : String,
  displayName      : String,
  statistics       : UserStatistics,
  wallet           : Wallet,
  currentProfession: Profession,
  locale           : Locale,
  identifier       : PlayerUuid
  // @formatter:on
) : UserImpl(
  name,
  displayName,
  statistics,
  wallet,
  currentProfession,
  locale,
  identifier
), LocalUser {

  /**
   * Lazy-loaded cache for storing temporary or frequently accessed data
   * specific to this user.
   *
   * The cache is instantiated only when accessed for the first time.
   *
   * @since 0.1.0
   */
  private val _cache: LocalUserCache by lazy {
    LocalUserCacheImpl()
  }

  /**
   * The performer responsible for executing actions and handling
   * communications on behalf of this user.
   *
   * @since 0.1.0
   */
  private val _performer: LocalUserPerformer = LocalUserPerformerImpl(
    this.identifier,
    this
  )

  override val cache: LocalUserCache
    get() = this._cache

  override val performer: LocalUserPerformer
    get() = this._performer

  override val game: LocalGame?
    get() = GameManager.findByUser<LocalTeam, LocalGame>(this)

  override val team: LocalTeam?
    get() = this.game?.findTeamByHostage(this)

  override val teammate: Teammate?
    get() = this.game?.findTeammateByHostage(this)

  override fun updateCurrentProfession(profession: Profession) {
    this.currentProfession = profession
    this.markAsModified()
  }

  override fun asLocalUser(): LocalUser {
    return this
  }

  override fun sendMessage(message: String) {
    this._performer.sendMessage(message)
  }

  override fun sendMessages(vararg messages: String) {
    this._performer.sendMessages(*messages)
  }
}