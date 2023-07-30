package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.shared.IdentifiableByName
import me.kvdpxne.dtm.user.User
import java.time.Instant
import java.util.*

class Game(val identifier: UUID, var name: String) {

  companion object {

    private val logger: KLogger = KotlinLogging.logger { }
  }

  /**
   * Map of users who have been signed up for this game.
   */
  val hostages: MutableMap<UUID, User>

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
    hostages = mutableMapOf()
    teams = mutableListOf()

    state = GameState.INITIALIZED

    arena = null
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

  /**
   * Checks if the given [user] is in the game.
   */
  fun isInGame(user: User): Boolean = null != hostages[user.identifier]

  /**
   * Checks if the given [user] is in any team.
   */
  fun isInTeam(user: User): Boolean = isInGame(user) && teams.any {
    // Checks if the given user is in the given team.
    it.isInTeam(user)
  }

  fun addUserToGame(user: User): User? = user.run {
    hostages.put(identifier, this)
  }.also {
    logger.debug {
      "Added user $user to $this game."
    }
  }

  fun removeUserFromGame(user: User): User? = user.run {
    hostages.remove(identifier)
  }.also {
    logger.debug {
      "Removed user $user from $this game."
    }
  }
}