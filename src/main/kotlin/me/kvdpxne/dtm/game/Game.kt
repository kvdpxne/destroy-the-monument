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

  /**
   *
   */
  val teams: MutableCollection<Team>


  val start: Instant

  val end: Instant?

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
    teams = mutableSetOf()

    state = GameState.INITIALIZED

    arena = null

    start = Instant.now()
    end = null
  }

  fun findTeam(identity: IdentifiableByName): Team? = teams.find {
    it.name == identity
  }

  /**
   * @return True if the given [team] does not exist in the [teams] collection
   * and has been successfully added to the [teams] collection, false if the
   * given [team] already exists in the [teams] collection and has not been
   * added to the [teams] collection.
   */
  fun addTeam(team: Team): Boolean = teams.add(team).also {
    if (it) {
      logger.debug {
        "A new $team team has been added to the $this game."
      }
    }
  }

  /**
   * @return True if the given [team] was in the [teams] collection and was
   * successfully removed, false if the given [team] was not in the [teams]
   * collection and was not removed.
   */
  fun removeTeam(team: Team): Boolean = teams.remove(team).also {
    if (it) {
      logger.debug {
        "Removed the $team team from the $this game."
      }
    }
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

  fun isInTeam(
    identity: IdentifiableByName,
    user: User
  ): Boolean {
    if (!isInGame(user)) {
      return false
    }

    val team = teams.find { it.name == identity } ?: return false
    return team.isInTeam(user)
  }

  fun addHostage(user: User): User? = user.run {
    hostages.put(identifier, this)
  }.also {
    logger.debug {
      "Added user $user to $this game."
    }
  }

  fun removeHostage(user: User): User? = user.run {
    hostages.remove(identifier)
  }.also {
    logger.debug {
      "Removed user $user from $this game."
    }
  }

  fun addTeammate(identity: IdentifiableByName, user: User) {
    var team = findTeam(identity)
    if (null == team) {
      team = Team(identity)
      (teams as MutableList<Team>) += team
    }
    val teammate = Teammate(user, DefaultTeamColor.viaIdentity(identity.identifiableName)!!)
    team.teammates += teammate
  }
}