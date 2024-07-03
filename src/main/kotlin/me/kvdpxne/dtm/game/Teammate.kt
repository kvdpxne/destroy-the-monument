package me.kvdpxne.dtm.game

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.shared.QueuingPair
import me.kvdpxne.dtm.shared.toQueuingPair
import me.kvdpxne.dtm.statistics.Statistics
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserPerformer

class Teammate(
  val user: User,
  var teamColor: Identity,
  val team: Team
): Communicative {

  /**
   *
   */
  val professionQueuingPair: QueuingPair<Profession>

  /**
   *
   */
  val statistics: Statistics

  /**
   *
   */
  var fastBoard: FastBoard?

  /**
   *
   */
  init {
    this.professionQueuingPair = this.user.profession.toQueuingPair()
    this.statistics = Statistics()


    this.fastBoard = null
  }

  /**
   * Alias for [User.sendMessage]
   *
   * @since 0.1
   */
  override fun sendMessage(
    message: String
  ) {
    this.user.sendMessage(message)
  }

  /**
   * Alias for [User.sendMessages]
   *
   * @since 0.1
   */
  override fun sendMessages(
    vararg messageArray: String
  ) {
    this.user.sendMessages(*messageArray)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Teammate

    return user == other.user
  }

  override fun hashCode(): Int {
    return user.hashCode()
  }

  override fun toString(): String {
    return "Teammate(user=$user, team=$team)"
  }
}