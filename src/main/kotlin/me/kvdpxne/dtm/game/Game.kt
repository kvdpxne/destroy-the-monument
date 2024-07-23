package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Instant
import java.util.UUID
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.data.GameArenasDao
import me.kvdpxne.dtm.data.GameTeamsDao
import me.kvdpxne.dtm.scoreboard.createServerScoreboard
import me.kvdpxne.dtm.scoreboard.createServerTeam
import me.kvdpxne.dtm.scoreboard.initScoreboard
import me.kvdpxne.dtm.shared.debug
import me.kvdpxne.dtm.shared.fillExperienceBar
import me.kvdpxne.dtm.shared.hardClean
import me.kvdpxne.dtm.tasks.GameStartTaskTimer
import me.kvdpxne.dtm.tasks.GameTimeUpdateTaskTimer
import me.kvdpxne.dtm.user.User
import org.bukkit.Bukkit

val MIN_HOSTAGE_SIZE_ = 2

private val logger: KLogger = KotlinLogging.logger { }

class Game(val identifier: UUID, var name: String) : Communicative {

  /**
   * Map of users who have been signed up for this game.
   */
  val hostages: MutableMap<UUID, User>

  /**
   *
   */
  val teams: MutableCollection<Team> = mutableSetOf()

  /**
   *
   */
  val teamHealthMutableMap: MutableMap<TeamIdentity, Int>

  /**
   *
   */
  val teamSizeMutableMap: MutableMap<TeamIdentity, Int>

  /**
   * The current arena where the game will be, is or was played.
   */
  var arenas: MutableCollection<Arena> = mutableSetOf()

  /**
   *
   */
  var currentArena: Arena? = null

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

  /**
   *
   */
  var timerTaskIdentifier = -1

  /**
   *
   */
  init {
    //
    this.hostages = mutableMapOf()

    //
    this.teamHealthMutableMap = mutableMapOf()
    this.teamSizeMutableMap = mutableMapOf()
  }

  /**
   *
   */
  fun findHostage(identifier: UUID): User? {
    return this.hostages[identifier]
  }

  /**
   *
   */
  fun findTeam(teamIdentity: TeamIdentity): Team? {
    return this.teams.find {
      it.identity == teamIdentity
    }
  }

  /**
   *
   */
  fun findTeam(user: User): Team? {
    return this.teams.find {
      it.hasTeammate(user)
    }
  }

  /**
   *
   */
  fun allTeamsAreSameSize(): Boolean {
    var size = -1
    for (team in teams) {
      if (-1 == size) {
        size = team.size()
        continue
      }
      if (size != team.size()) {
        return false
      }
    }
    return true
  }

  /**
   * @return The [Team] with fewer [Team.teammates], or null if no team is
   * assigned to the game.
   */
  fun findSmallerTeam(): Team? {
    return this.teams.minByOrNull {
      it.size()
    }
  }

  /**
   * Checks if the given [user] is in the game.
   */
  fun isInGame(user: User): Boolean {
    return this.hostages.contains(user.identifier)
  }

  /**
   * Checks if the given [user] is in any team.
   */
  fun isInTeam(user: User): Boolean {
    return this.teams.any {
      it.hasTeammate(user)
    }
  }

  /**
   * Checks if the given [user] is currently present in given team specified by
   * the [identity].
   *
   * @param identity
   * @param user
   */
  fun isInTeam(identity: TeamIdentity, user: () -> User): Boolean {
    return this.teams.find {
      it.identity == identity
    }?.hasTeammate(user()) ?: false
  }

  fun isInArenaMap(user: User): Boolean {
    return currentArena?.map?.world?.players?.any {
      it.uniqueId == user.identifier
    } ?: false
  }

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
  fun addTeam(team: Team): Boolean {
    if (null == team.game) {
      team.game = this
    }

    val result = teams.add(team).also {
      logger.debug(it) {
        "A new $team team has been added to the $this game."
      }
    }

    GameTeamsDao.insert(this, team)
    return result
  }

  fun addTeammate(
    teamIdentity: TeamIdentity,
    user: () -> User
  ): Boolean {
    //
    val team = this.findTeam(teamIdentity) ?: return false

    //
    val teammate = user()

    //
    if (team.hasTeammate(teammate)) {
      return false
    }

    teams.forEach {
      // Checking if the user is present in the team is not necessary because
      // the method to remove the user from the team filters the collections
      // of current users in the team to find the given user.
      if (!it.removeTeammate(teammate)) {
        return@forEach
      }

      this.teamSizeMutableMap[teamIdentity] = team.size()
      ++spectators
    }

    if (team.addTeammate(teammate).not()) {
      return false
    }

    this.teamSizeMutableMap[teamIdentity] = team.size()

    logger.debug {
      "A new $teammate teammate has been added to the $team team in the " +
        "$this game."
    }

    // If the user has successfully joined the team, the number of users
    // (spectators) who are currently not playing should decrease.
    --spectators

    if (this.playersInGame() >= MIN_HOSTAGE_SIZE_ && state.isInitialized()) {
      state = GameState.STARTING

      // TODO task
      GameStartTaskTimer(this).runTaskTimerAsynchronously(
        DestroyTheMonument.instance,
        10L,
        20L
      )
    }
    return true
  }

  /**
   *
   */
  fun playersInGame(): Int {
    return this.hostages.size - this.spectators
  }

  /**
   *
   */
  fun addArena(arena: Arena) {
    this.arenas.add(arena)
    GameArenasDao.insert(this, arena)
  }

  /**
   *
   */
  fun removeHostage(user: User): Boolean {
    return findHostage(user.identifier)?.run {
      // If the user is in any team, he should be removed from that team before
      // he is removed from the whole game.
      findTeam(this)?.run {
        removeTeammate(user)
        // If a player does not belong to any team during his tenure in this game
        // then he has never stopped being a spectator.
        ++spectators
      }
      hostages -= identifier
      logger.debug {
        "Removed $this user from ${this@Game} game."
      }
      true
    } ?: false
  }

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
  fun removeTeam(team: Team): Boolean {
    return teams.remove(team).also {
      if (it) {
        logger.debug {
          "Removed the $team team from the $this game."
        }
      }
    }
  }

  /**
   *
   */
  fun removeTeammate(
    teamIdentity: TeamIdentity,
    user: () -> User
  ): Boolean {
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

  /**
   *
   */
  fun start() {
    state = GameState.STARTED
    start = Instant.now()

    val arena = arenas.random()
    currentArena = arena

    this.teams.forEach { team ->
      val identity = team.identity
      arena.monuments[identity]?.let {
        this.teamHealthMutableMap[identity] = it.size
      }
    }

    //
    val bukkitTeamScoreboard = createServerScoreboard()

    //
    val timeTask = GameTimeUpdateTaskTimer(this)


    val teamsIdentity = teams.map { it.identity }.toTypedArray()

    teams.forEach { team ->

      val bukkitTeam = createServerTeam(
        bukkitTeamScoreboard,
        team.identity.name,
        team.identity.colorInChat
      )

      team.health = arena.monuments.size

      val location = arena.spawnPoints[team.identity]?.let {
        val world = arena.map?.world ?: return@forEach
        it.toLocation(world)
      }

      team.teammates.forEach { teammate ->
        val profession = teammate.professionQueuingPair.current
        val performer = teammate.user.performer

        performer.player!!.run {
          this.teleport(location)
          this.hardClean()

          scoreboard = bukkitTeamScoreboard
          bukkitTeam.addPlayer(this)

          val fastBoard = initScoreboard(
            this,
            teamSizeMutableMap[teamsIdentity[0]] ?: 0,
            teamHealthMutableMap[teamsIdentity[1]] ?: 0,
            teamSizeMutableMap[teamsIdentity[0]] ?: 0,
            teamHealthMutableMap[teamsIdentity[1]] ?: 0,
            teammate.user.wallet.coins
          )

          teammate.fastBoard = fastBoard
          timeTask.playerMutableList += fastBoard

          profession.equip(this, teammate.team.identity.dyeColor)
          profession.ability?.let {
            if (it.readyAfterDeath) {
              it.markReady()
              it.whenReady(this)

              // Fill player exp bar after 200 ms
              Bukkit.getScheduler().runTaskLaterAsynchronously(
                DestroyTheMonument.instance,
                { this.fillExperienceBar() },
                4L
              )
              return
            }

            it.run(this, true)
          }
        }
      }
    }

    timerTaskIdentifier = timeTask.runTaskTimerAsynchronously(
      DestroyTheMonument.instance,
      20L,
      20L
    ).taskId
  }

  /**
   *
   */
  fun stop() {
    state = GameState.STOPPING

    this.currentArena!!.map!!.unload()
    this.currentArena = null

    if (timerTaskIdentifier >= 0) {
      Bukkit.getScheduler().cancelTask(timerTaskIdentifier)
      timerTaskIdentifier = -1
    }

    teams.forEach { team ->
      team.teammates.forEach { teammate ->

        val performer = teammate.user.performer

        teammate.fastBoard!!.delete()
        teammate.fastBoard = null

        performer.player!!.run {
          this.scoreboard.getPlayerTeam(this).removePlayer(this)
          this.scoreboard = Bukkit.getScoreboardManager().mainScoreboard
          this.hardClean()
        }
      }

      // Clean
      team.removeAllTeammates()
    }

    // TODO remove
    this.teamHealthMutableMap.clear()
    this.teamSizeMutableMap.clear()

    this.spectators = this.hostages.size

    end = Instant.now()
    state = GameState.STOPPED

    // Reinitializing
    state = GameState.INITIALIZED
  }

  /**
   * Alias for [User.sendMessage]
   */
  override fun sendMessage(
    message: String
  ) {
    if (this.hostages.isEmpty()) {
      return
    }

    this.hostages.values.forEach { user: User ->
      user.sendMessage(message)
    }
  }

  /**
   * Alias for [User.sendMessage]
   */
  override fun sendMessage(
    message: () -> String
  ) {
    if (this.hostages.isEmpty()) {
      return
    }

    val body = message()
    this.hostages.values.forEach { user: User ->
      user.sendMessage(body)
    }
  }

  /**
   * Alias for [User.sendMessages]
   */
  override fun sendMessages(
    vararg messageArray: String
  ) {
    if (this.hostages.isEmpty() || messageArray.isEmpty()) {
      return
    }

    this.hostages.values.forEach { user: User ->
      user.sendMessages(*messageArray)
    }
  }

  override fun toString(): String {
    return "Game(identifier='$identifier', name='$name', state=$state)"
  }
}