package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.IdentifiableByName
import me.kvdpxne.dtm.user.User

class Team(val name: IdentifiableByName) {

  val teammates = mutableListOf<Teammate>()

  fun isInTeam(user: User): Boolean = null != teammates.find { it.user == user }
}