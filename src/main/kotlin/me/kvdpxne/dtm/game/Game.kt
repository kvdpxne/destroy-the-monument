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
  val hostages: MutableMap<UUID, User> = mutableMapOf()

  /**
   *
   */
  val teams: MutableCollection<Team> = mutableSetOf()

  /**
   * The current arena where the game will be, is or was played.
   */
  var arena: Arena? = null

  /**
   *
   */
  val start: Instant = Instant.now()

  /**
   *
   */
  var end: Instant? = null

  /**
   * The current state of the game object.
   */
  var state: GameState = GameState.INITIALIZED

  /**
   *
   */
  var spectators: Int = 0

  fun findHostage(identifier: UUID): User? = hostages[identifier]

  fun findTeam(identity: IdentifiableByName): Team? = teams.find {
    it.name == identity
  }

  fun findTeam(user: User): Team? = teams.find {
    it.hasTeammate(user)
  }

  /**
   * Checks if the given [user] is in the game.
   */
  fun isInGame(user: User): Boolean = hostages.contains(user.identifier)

  /**
   * Checks if the given [user] is in any team.
   */
  fun isInTeam(user: User): Boolean = teams.any {
    it.hasTeammate(user)
  }

  /**
   * Checks if the given [user] is currently present in given team specified by
   * the [identity].
   *
   * @param identity
   * @param user
   */
  fun isInTeam(
    identity: IdentifiableByName,
    user: () -> User
  ): Boolean = teams.find {
    it.name == identity
  }?.hasTeammate(user()) ?: false

  /**
   *
   */
  fun addHostage(user: User): Boolean {
    if (isInGame(user)) {
      return false
    }
    hostages[user.identifier] = user
    ++spectators
    logger.debug {
      "A new $user user has been added to the $this game."
    }
    return true
  }

  /**
   * Tries to add the given [team] to the [teams] collection if the given
   * [team] is currently not present in the [teams] collection.
   *
   * @param team The currently not present [team] in the [teams] collection to
   * be added to the [teams] collection.
   *
   * @return True if the given [team] does not exist in the [teams] collection
   * and has been successfully added to the [teams] collection, false if the
   * given [team] already exists in the [teams] collection and has not been
   * added to the [teams] collection.
   */
  fun addTeam(team: Team) = teams.add(team).also {
    if (it) {
      logger.debug {
        "A new $team team has been added to the $this game."
      }
    }
  }

  fun addTeammate(
    identity: IdentifiableByName,
    userProvider: () -> User
  ): Boolean {
    val team = findTeam(identity) ?: return false
    val teammate = Teammate(userProvider(), DefaultTeamColor.viaIdentity(identity.identifiableName)!!)
    team.teammates += teammate
    --spectators
    return true
  }

  /**
   *
   */
  fun removeHostage(user: User) = findHostage(user.identifier)?.run {
    // If the user is in any team, he should be removed from that team before
    // he is removed from the whole game.
    val team = findTeam(this)?.run {
      removeTeammate(user)
    }
    hostages -= identifier
    logger.debug {
      "Removed $this user from ${this@Game} game."
    }
    // If a player does not belong to any team during his tenure in this game
    // then he has never stopped being a spectator.
    team ?: --spectators
    true
  } ?: false

  /**
   * Tries to remove the given [team] from the [teams] collection if the given
   * [team] is currently present in the [teams] collection.
   *
   * @param team The currently present [team] in the [teams] collection to be
   * removed from the [teams] collection.
   *
   * @return True if the given [team] was in the [teams] collection and was
   * successfully removed, false if the given [team] was not in the [teams]
   * collection and was not removed.
   */
  fun removeTeam(team: Team) = teams.remove(team).also {
    if (it) {
      logger.debug {
        "Removed the $team team from the $this game."
      }
    }
  }

  fun removeTeammate(
    identity: IdentifiableByName,
    userProvider: () -> User
  ): Boolean {
    val team = findTeam(identity) ?: return false
    return true
  }
}