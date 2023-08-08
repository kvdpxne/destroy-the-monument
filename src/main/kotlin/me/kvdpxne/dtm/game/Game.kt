package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.data.GameArenasDao
import me.kvdpxne.dtm.data.GameArenasTable
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserPerformer
import org.bukkit.Location
import org.bukkit.entity.Player
import java.time.Instant
import java.util.UUID

private val logger: KLogger = KotlinLogging.logger { }

class Game(val identifier: UUID, var name: String) {

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
  var arenas: MutableCollection<Arena> = mutableSetOf()

  /**
   * The start time of a phase of the game.
   */
  var start: Instant = Instant.now()

  /**
   * The end time of a phase of the game.
   */
  var end: Instant? = null

  /**
   * The current state of the game object.
   */
  var state: GameState = GameState.INITIALIZED

  /**
   * Number of users present in the game but not currently playing.
   */
  var spectators: Int = 0

  fun findHostage(identifier: UUID): User? = hostages[identifier]

  fun findTeam(identity: Identity): Team? = teams.find {
    it.identity == identity
  }

  fun findTeam(user: User): Team? = teams.find {
    it.hasTeammate(user)
  }

  /**
   * @return The [Team] with fewer [Team.teammates], or null if no team is
   * assigned to the game.
   */
  fun findSmallerTeam(): Team? = teams.minByOrNull {
    it.size()
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
  fun isInTeam(identity: Identity, user: () -> User): Boolean = teams.find {
    it.identity == identity
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

  fun addTeammate(identity: Identity, user: () -> User): Boolean {
    // Team identity with which the team to which the given user is to be added
    // is identified.
    val teamIdentity = DefaultTeamColor.findByIdentity(identity) ?: return false
    val team = findTeam(teamIdentity) ?: return false
    val teammate = user()

    if (team.hasTeammate(teammate)) {
      return false
    }

    teams.forEach {
      // Checking if the user is present in the team is not necessary because
      // the method to remove the user from the team filters the collections
      // of current users in the team to find the given user.
      it.removeTeammate(teammate)
    }

    //
    //
    return team.addTeammate(teammate).also {
      if (!it) {
        return@also
      }
      logger.debug {
        "A new $teammate teammate has been added to the $team team in the " +
          "$this game."
      }
      // If the user has successfully joined the team, the number of users
      // (spectators) who are currently not playing should decrease.
      --spectators
    }
  }

  fun addArena(arena: Arena) {
    this.arenas.add(arena)
    GameArenasDao.insert(this, arena)
  }

  /**
   *
   */
  fun removeHostage(user: User) = findHostage(user.identifier)?.run {
    // If the user is in any team, he should be removed from that team before
    // he is removed from the whole game.
    findTeam(this)?.run {
      removeTeammate(user)
      // If a player does not belong to any team during his tenure in this game
      // then he has never stopped being a spectator.
      --spectators
    }
    hostages -= identifier
    logger.debug {
      "Removed $this user from ${this@Game} game."
    }
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
    identity: Identity,
    user: () -> User
  ): Boolean {
    val teamIdentity = DefaultTeamColor.findByIdentity(identity) ?: return false
    val team = findTeam(teamIdentity) ?: return false
    val teammate = user()
    return team.removeTeammate(teammate).also {
      if (!it) {
        return@also
      }
      logger.debug {

      }
      ++spectators
    }
  }

  fun start(user: User) {
    end = Instant.now()
    state = GameState.STARTING

//    logger.debug {
//      "The game took off after the FSF time."
//    }

    val userTeam = findTeam(user) ?: return
    val arena = arenas.random()
    arena.spawnPoints[userTeam.identity].let {
      user.performer as UserPerformer

      // TODO XD
      it!!
      val location = Location(arena.map?.getWorld(), it.x, it.y, it.z, it.pitch, it.yaw)

      user.performer.getPlayer()?.teleport(location)
    }

  }

  fun stop() {

  }

  override fun toString(): String {
    return "Game(identifier='$identifier', name='$name', state=$state)"
  }
}