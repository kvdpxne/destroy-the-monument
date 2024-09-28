package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalTeam
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.wallet.Wallet

class LocalUserImpl(
  // @formatter:off
  name             : String,
  displayName      : String,
  statistics       : UserStatistics,
  wallet           : Wallet,
  currentProfession: Profession,
  identifier       : UUID
  // @formatter:on
) : UserImpl(
  name,
  displayName,
  statistics,
  wallet,
  currentProfession,
  identifier
), LocalUser {

  private val _cache: LocalUserCache by lazy {
    LocalUserCacheImpl()
  }

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
    get() = this.team?.getTeammate(this)

  override fun updateCurrentProfession(profession: Profession) {
    this.currentProfession = profession
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