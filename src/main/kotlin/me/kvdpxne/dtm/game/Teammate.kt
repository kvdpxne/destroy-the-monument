package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.user.User

class Teammate(
  val user: User,
  var teamColor: Identity,
  val team: Team
) {

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