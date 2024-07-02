package me.kvdpxne.dtm.game

import fr.mrmicky.fastboard.FastBoard
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
) {

  /**
   *
   */
  val professionQueuingPair: QueuingPair<Profession> =
    user.profession.toQueuingPair()

  val statistics: Statistics = Statistics()

  var fastBoard: FastBoard? = null

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