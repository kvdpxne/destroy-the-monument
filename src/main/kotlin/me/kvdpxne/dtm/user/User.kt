package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.wallet.Wallet

class User(
  // @formatter:off
  val name       : String,
  statistics : UserStatistics = UserStatistics(),
  wallet     : Wallet         = Wallet(),
  identifier : UUID
  // @formatter:on
) : OfflineUser(statistics, wallet, identifier), Communicative {

  /**
   * @since 0.1.0
   */
  // current profession
  var currentProfession: Profession? = ProfessionManager
    .filter { it.enabled }
    .randomOrNull()
//    set(value) {
//      if (!this._availableProfessions.contains(value)) {
//        throw IllegalArgumentException("Profession $value is already in use.")
//      }
//
//      field = value
//    }

  /**
   * @since 0.1.0
   */
  val performer: UserPerformer = UserPerformer(this.identifier, this.name, this)

  val game: Game?
    get() = GameManager.findByUser(this)

  val team: Team?
    get() = this.game?.findTeam(this)

  val teammate: Teammate?
    get() = this.game?.findTeammate(this)

  /**
   * Alias for [UserPerformer.sendMessage]
   *
   * @since 0.1
   */
  override fun sendMessage(
    message: String
  ) {
    this.performer.sendMessage(message)
  }

  /**
   * Alias for [UserPerformer.sendMessages]
   *
   * @since 0.1
   */
  override fun sendMessages(
    vararg messageArray: String
  ) {
    this.performer.sendMessages(*messageArray)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as User

    if (identifier != other.identifier) return false
    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    var result = identifier.hashCode()
    result = 31 * result + name.hashCode()
    return result
  }

  override fun toString(): String {
    return "User(identifier=$identifier, name='$name')"
  }
}