package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.scoreboard.createServerScoreboard
import me.kvdpxne.dtm.scoreboard.createServerTeam
import me.kvdpxne.dtm.scoreboard.initScoreboard
import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancelTask
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipB
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.runAsynchronousDelayedRepeatingTask
import me.kvdpxne.dtm.shared.minecraft.bukkit.toLocation
import me.kvdpxne.dtm.tasks.GameStartAsyncTask
import me.kvdpxne.dtm.tasks.GameTimeUpdateTaskTimer
import me.kvdpxne.dtm.uid.Uid
import me.kvdpxne.dtm.user.User
import org.bukkit.Bukkit
import org.bukkit.Location

val MIN_HOSTAGE_SIZE_ = 2

private val logger: KLogger = KotlinLogging.logger { }

class Game(
  // @formatter:off
  val name      : String,
      identifier: String = Uid.next(),
  // @formatter:on
) : AbstractIdentifiable<String>(identifier), Communicative {

  /**
   * Map of users who have been signed up for this game.
   *
   * @since 0.1.0
   */
  private val _hostages: MutableMap<UUID, User> = mutableMapOf()

  /**
   * A mutable map storing teams by their identifiers.
   *
   * This map is used internally to manage and access team information.
   *
   * @since 0.1.0
   */
  private val _teams: MutableMap<String, Team> = mutableMapOf()

  /**
   * The current arena where the game will be, is or was played.
   *
   * @since 0.1.0
   */
  private var _arenas: MutableSet<Arena> = mutableSetOf()

  /**
   * @since 0.1.0
   */
  var currentArena: Arena? = null

  /**
   * Represents the current state of the game.
   *
   * @since 0.1.0
   */
  var state: Int = GameStates.INITIALIZED

  /**
   * Number of users present in the game but not currently playing.
   *
   * @since 0.1.0
   */
  var spectators: Int = 0

  /**
   * @since 0.1.0
   */
  var timerTaskIdentifier: Int = -1

  /**
   * @since 0.1.0
   */
  val hostages: Array<User>
    get() = this._hostages.values.toTypedArray()

  /**
   * @since 0.1.0
   */
  val teams: List<Team>
    get() = this._teams.values.toList()

  /**
   * @since 0.1.0
   */
  val arenas: Array<Arena>
    get() = this._arenas.toTypedArray()

  /**
   * @since 0.1.0
   */
  val smallestTeam: Team
    get() = this._teams.values.minBy {
      it.size
    }

  /**
   * @since 0.1.0
   */
  val largestTeam: Team
    get() = this._teams.values.maxBy {
      it.size
    }

  /**
   * @since 0.1.0
   */
  val randomTeam: Team
    get() = this._teams.values.random()

  /**
   * The game has been initialized and is ready to start.
   *
   * @since 0.1.0
   */
  val isInitialized: Boolean
    get() = GameStates.INITIALIZED == this.state

  /**
   * The game is in the process of starting.
   *
   * @since 0.1.0
   */
  val isStarting: Boolean
    get() = GameStates.STARTING == this.state

  /**
   * The game is currently running.
   *
   * @since 0.1.0
   */
  val isRunning: Boolean
    get() = GameStates.RUNNING == this.state

  /**
   * The game is in the process of stopping.
   *
   * @since 0.1.0
   */
  val isStopping: Boolean
    get() = GameStates.STOPPING == this.state

  /**
   * Determines if all teams have the same number of members.
   *
   * This function iterates through each team, checking if their size matches
   * the first team's size. If a team with a different size is found, it
   * immediately returns `false`. Otherwise, it returns `true`.
   *
   * @return True if all teams have the same size, false otherwise.
   *
   * @since 0.1.0
   */
  val isTeamsSameSize: Boolean
    get() {
      val first = this._teams.values.firstOrNull()?.size ?: return false
      return this._teams.values.all {
        it.size == first
      }
    }

  /**
   * @since 0.1.0
   */
  val numberOfHostages: Int
    get() = this._hostages.size

  /**
   * @since 0.1.0
   */
  val numberOfHostagesEnrolled: Int
    get() = this._hostages.size - this.spectators

  /**
   * @since 0.1.0
   */
  val numberOfTeams: Int
    get() = this._teams.size

  /**
   * @since 0.1.0
   */
  private fun nextArena(): Arena {
    var arena = this.currentArena
    if (null != arena) {
      return arena
    }

    arena = this._arenas.random()
    this.currentArena = arena
    return arena
  }

  /**
   * @since 0.1.0
   */
  private fun shouldStart() {
    if (!this.isInitialized || MIN_HOSTAGE_SIZE_ > this.numberOfHostagesEnrolled) {
      return
    }

    runAsynchronousDelayedRepeatingTask(10L, 20L) {
      GameStartAsyncTask(this)
    }
  }

  /**
   * @since 0.1.0
   */
  private fun shouldMoveTeammate(
    teammate: Teammate
  ) {
    if (!this.isRunning) {
      return
    }

    val team = teammate.team
    val arena = this.nextArena()

    val bukkitTeamScoreboard = createServerScoreboard()

    val bukkitTeam = createServerTeam(
      bukkitTeamScoreboard,
      team.identity.name,
      team.identity.colorInChat
    )

    // TODO precise
    team.health = arena.monumentPositions.size / 2

    val location = arena.findRevivalPosition(team.identity)?.let {
      val world = arena.map?.world ?: return
      it.toLocation(world)
    }

    val player = teammate.user.performer.player!!
    this.fsf(teammate, location!!)

    player.scoreboard = bukkitTeamScoreboard
    bukkitTeam.addPlayer(player)
  }

  /**
   * @since 0.1.0
   */
  fun findHostage(
    identifier: UUID
  ): User? {
    return this._hostages[identifier]
  }

  /**
   * @since 0.1.0
   */
  fun findTeamByIdentifier(
    teamIdentity: TeamIdentity
  ): Team? {
    return this._teams[teamIdentity.identifier]
  }

  /**
   * @since 0.1.0
   */
  fun findTeam(
    user: User
  ): Team? {
    return this._teams.values.find {
      it.hasTeammate(user)
    }
  }

  /**
   * @since 0.1.0
   */
  fun findTeammate(
    user: User
  ): Teammate? {
    return this._teams.values.firstNotNullOfOrNull {
      it.findTeammate(user)
    }
  }

  /**
   * Checks if a team with the specified identifier exists.
   *
   * This function determines if a team with the given `identifier` is present
   * in the internal `_teams` map.
   *
   * @param identifier The identifier of the team to check.
   * @return `true` if a team with the given identifier exists, `false`
   *         otherwise.
   * @throws IllegalArgumentException If the provided `identifier` is blank.
   *
   * @since 0.1.0
   */
  fun hasTeam(
    identifier: String
  ): Boolean {
    require(identifier.isNotBlank()) {
      "The given identifier must not be blank."
    }

    return this._teams.containsKey(identifier)
  }

  /**
   * Checks if an arena with the specified identifier exists.
   *
   * This function iterates over the internal `_arenas` collection to determine
   * if an arena with the given `identifier` is present.
   *
   * @param identifier The identifier of the arena to check.
   * @return `true` if an arena with the given identifier exists, `false`
   *         otherwise.
   * @throws IllegalArgumentException If the provided `identifier` is blank.
   *
   * @since 0.1.0
   */
  fun hasArena(
    identifier: String
  ): Boolean {
    require(identifier.isNotBlank()) {
      "The given identifier must not be blank."
    }

    return this._arenas.any {
      it.identifier == identifier
    }
  }

  /**
   * Checks if the given [user] is in the game.
   */
  fun isInGame(
    user: User
  ): Boolean {
    return this._hostages.contains(user.identifier)
  }

  /**
   * Checks if the given [user] is in any team.
   */
  fun isInTeam(
    user: User
  ): Boolean {
    return this._teams.values.any {
      it.hasTeammate(user)
    }
  }

  /**
   * @since 0.1.0
   */
  fun isInArena(
    user: User
  ): Boolean {
    if (!this.isRunning || this.isStopping) {
      return false
    }

    //
    val arena = this.currentArena?.map?.world ?: throw RuntimeException("")

    //
    return arena.players.any {
      it.uniqueId == user.identifier
    }
  }

  /**
   * @since 0.1.0
   */
  fun addHostage(
    user: User
  ): Boolean {
    if (this.isInGame(user)) {
      return false
    }

    this._hostages[user.identifier] = user
    ++this.spectators

    return true
  }

  /**
   * Tries to add the given [team] to the [_teams] collection if the given
   * [team] is currently not present in the [_teams] collection.
   *
   * @param team The currently not present [team] in the [_teams] collection to
   * be added to the [_teams] collection.
   *
   * @return True if the given [team] does not exist in the [_teams] collection
   * and has been successfully added to the [_teams] collection, false if the
   * given [team] already exists in the [_teams] collection and has not been
   * added to the [_teams] collection.
   */
  fun addTeam(
    team: Team
  ): Boolean {
    if (this.hasTeam(team.identity.identifier)) {
      return false
    }

    if (null == team.game) {
      team.game = this
    }

    this._teams[team.identity.identifier] = team
    return true
  }

  /**
   * @since 0.1.0
   */
  fun addTeammate(
    teamIdentity: TeamIdentity,
    user: User
  ): Boolean {
    //
    val team = this.findTeamByIdentifier(teamIdentity) ?: return false

    //
    if (team.hasTeammate(user)) {
      return false
    }

    //
    val teammate = Teammate(user, team, this)

    //
    this._teams.values.forEach {
      // Checking if the user is present in the team is not necessary because
      // the method to remove the user from the team filters the collections
      // of current users in the team to find the given user.
      if (!it.removeTeammate(teammate)) {
        return@forEach
      }

      ++this.spectators
    }

    //
    if (!team.addTeammate(teammate)) {
      throw IllegalStateException("")
    }

    // If the user has successfully joined the team, the number of users
    // (spectators) who are currently not playing should decrease.
    --this.spectators

    //
    this.shouldStart()

    //
    this.shouldMoveTeammate(teammate)

    //
    return true
  }

  /**
   * @since 0.1.0
   */
  fun addArena(
    arena: Arena
  ) {
    this._arenas.add(arena)
  }

  /**
   *
   */
  fun removeTeammate(
    teamIdentity: TeamIdentity,
    user: User
  ): Boolean {
    //
    val team = this.findTeamByIdentifier(teamIdentity) ?: return false

    if (!team.removeTeammate(user)) {
      return false
    }

    ++this.spectators
    return true
  }

  /**
   * @since 0.1.0
   */
  fun removeTeam(
    team: Team
  ): Boolean {
    if (0 != team.size) {
      team.teammates.forEach {
        if (!team.removeTeammate(it)) {
          return@forEach
        }

        ++this.spectators
      }
    }

    this._teams.remove(team.identity.identifier)
    return this.hasTeam(team.identity.identifier)
  }

  /**
   * @since 0.1.0
   */
  fun removeHostage(
    user: User
  ): Boolean {
    val hostage = this.findHostage(user.identifier) ?: return false
    this._hostages.remove(user.identifier)
    val team = this.findTeam(hostage) ?: return true
    this.removeTeammate(team.identity, hostage)
    return true
  }

  /**
   * @since 0.1.0
   */
  fun removeArena(
    arena: Arena
  ) {
    this._arenas.remove(arena)
  }

  fun fsf(teammate: Teammate, spawn: Location) {
    val player = teammate.user.performer.player ?: throw IllegalStateException("Player not found")

    player.teleport(spawn)
    player.reset()

    teammate.currentProfession.equip(player, teammate.team.identity.dyeColor)
    teammate.currentProfession.ability?.renewDelayed(player, true)
  }

  var timerTask: GameTimeUpdateTaskTimer? = null

  /**
   *
   */
  fun start() {
    check(!this.isRunning) {
      "Cannot start the game because it is currently running."
    }

    check(!this.isStopping) {
      "Cannot start game because it is currently stopping."
    }

    //
    val arena = this.nextArena()

    //
    val bukkitTeamScoreboard = createServerScoreboard()

    //
    this.timerTask = GameTimeUpdateTaskTimer(this)

    //
    val signedTeams = this.teams

    //
    for (team: Team in signedTeams) {
      team.health = arena.monumentPositions.size / 2 // TODO stupid
    }

    //
    //
    val teamPair = Pair(signedTeams[0], signedTeams[1])

    for (team: Team in signedTeams) {

      val bukkitTeam = createServerTeam(
        bukkitTeamScoreboard,
        team.identity.name,
        team.identity.colorInChat
      )

      val location = arena.findRevivalPosition(team.identity)?.let {
        val world = arena.map?.world!!
        it.toLocation(world)
      }

      for (teammate: Teammate in team.teammates) {
        val player = teammate.user.performer.player!!

        this.fsf(teammate, location!!)

        player.scoreboard = bukkitTeamScoreboard
        bukkitTeam.addPlayer(player)

        val fastBoard = initScoreboard(
          player,
          teamPair.second.size,
          teamPair.second.health,
          teamPair.first.size,
          teamPair.first.health,
          teammate.user.wallet.coins
        )

        teammate.fastBoard = fastBoard
        timerTask!!.playerMutableList += fastBoard
      }
    }

    if (null != this.timerTask) {
      this.timerTaskIdentifier = this.timerTask!!.runTaskTimerAsynchronously(
        DestroyTheMonument.instance,
        20L,
        20L
      ).taskId
    }

    //
    this.state = GameStates.RUNNING
  }

  /**
   * @since 0.1.0
   */
  fun stop() {
    check(this.isRunning) {
      "Game cannot be stopped because it is not currently running."
    }

    //
    this.state = GameStates.STOPPING

    if (this.timerTaskIdentifier >= 0) {
      cancelTask(this.timerTaskIdentifier)
      this.timerTaskIdentifier = -1
    }

    //
    this._teams.values.forEach {

      it._teammates.forEach {

        it.currentProfession.ability?.cancelCooldown()
        it.fastBoard.delete()

        val player = it.user.performer.player!!

        player.scoreboard.getPlayerTeam(player).removePlayer(player)
        player.scoreboard = Bukkit.getScoreboardManager().mainScoreboard

        player.reset()
        player.equipB()
      }

      it.removeTeammates()
    }

    try {
      this.currentArena!!.map!!.unload()
      this.currentArena!!.monumentPositions.forEach {
        it.restore()
      }
      this.currentArena = null
    } catch (exception: Exception) {
      exception.printStackTrace()
    }

    this.spectators = this._hostages.size

    // Reinitializing
    this.state = GameStates.INITIALIZED
  }

  /**
   * Alias for [User.sendMessage]
   */
  override fun sendMessage(
    message: String
  ) {
    if (this._hostages.isEmpty()) {
      return
    }

    this._hostages.values.forEach { user: User ->
      user.sendMessage(message)
    }
  }

  /**
   * Alias for [User.sendMessage]
   */
  override fun sendMessage(
    message: () -> String
  ) {
    if (this._hostages.isEmpty()) {
      return
    }

    val body = message()
    this._hostages.values.forEach { user: User ->
      user.sendMessage(body)
    }
  }

  /**
   * Alias for [User.sendMessages]
   */
  override fun sendMessages(
    vararg messages: String
  ) {
    if (this._hostages.isEmpty() || messages.isEmpty()) {
      return
    }

    this._hostages.values.forEach { user: User ->
      user.sendMessages(*messages)
    }
  }

  override fun toString(): String {
    return "Game(identifier='$identifier', name='$name', state=$state)"
  }
}