package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.IdentifiableByName
import me.kvdpxne.dtm.user.User
import java.time.Instant
import java.util.UUID

class Game(val identifier: UUID, var name: String) {

  val teams: MutableCollection<Team>

  val startInstant: Instant

  /**
   * The current arena where the game will be, is or was played.
   */
  var arena: Arena?

  /**
   * The current state of the game object.
   */
  var state: GameState

  init {
    state = GameState.INITIALIZED

    arena = null
    teams = mutableListOf()
    startInstant = Instant.now()
  }

  fun addTeammate(identity: IdentifiableByName, user: User) {
    var team = teams.find { it.name == identity }
    if (null == team) {
      team = Team(identity)
      (teams as MutableList<Team>) += team
    }
    val teammate = Teammate(user, DefaultTeamColor.viaIdentity(identity.identifiableName)!!)
    team.teammates += teammate
  }
}