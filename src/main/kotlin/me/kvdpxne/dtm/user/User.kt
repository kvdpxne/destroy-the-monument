package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.ancillary.BaseMutable
import me.kvdpxne.dtm.wallet.Wallet

class User(
  // @formatter:off
  val identifier : UUID,
  var name       : String,
  var statistics : UserStatistics         = UserStatistics(),
  val wallet     : Wallet                 = Wallet(),
      professions: Collection<Profession> = emptySet()
  // @formatter:on
) : BaseMutable(), Communicative {

  /**
   * @since 0.1.0
   */
  // available professions
  private val _availableProfessions: MutableSet<Profession> = professions.toMutableSet()

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

  /**
   * @since 0.1.0
   */
  val availableProfessions: List<Profession>
    get() = this._availableProfessions.toList()

  val game: Game?
    get() = GameManager.findByUser(this)

  /**
   * @since 0.1.0
   */
  fun addProfession(profession: Profession): Boolean {
    return this._availableProfessions.add(profession)
  }

  /**
   * @since 0.1.0
   */
  fun removeProfession(
    profession: Profession
  ): Boolean {
    return this._availableProfessions.remove(profession)
  }

  /**
   * @since 0.1.0
   */
  fun removeProfessionByIdentifier(
    identifier: String
  ): Boolean {
    return this._availableProfessions.removeIf {
      it.identifier.equals(identifier, true)
    }
  }

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