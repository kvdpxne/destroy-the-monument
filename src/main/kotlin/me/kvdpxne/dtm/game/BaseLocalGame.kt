package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.scoreboard.createServerScoreboard
import me.kvdpxne.dtm.scoreboard.createServerTeam
import me.kvdpxne.dtm.scoreboard.initScoreboard
import me.kvdpxne.dtm.shared.debug.Debug
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

class BaseLocalGame(
  // @formatter:off
  name      : String,
  teams     : Map<String, LocalTeam> = emptyMap(),
  arenas    : Map<String, Arena>     = emptyMap(),
  hostages  : MutableMap<UUID, User> = mutableMapOf(),
  identifier: String                 = Uid.next()
  // @formatter:on
) : BaseGame<LocalTeam>(
  name,
  name,
  teams,
  arenas,
  identifier
), LocalGame {

  /**
   *
   */
  val _hostages: MutableMap<UUID, User> = mutableMapOf()

  var _currentArena: Arena? = null

  var _state: Int = GameStates.INITIALIZED

  /**
   * Number of users present in the game but not currently playing.
   *
   * @since 0.1.0
   */
  var spectators: Int = 0

  /**
   * @since 0.1.0
   */
  override var timerTaskIdentifier: Int = -1

  override val currentArena: Arena?
    get() = this._currentArena

  override val state: Int
    get() = this._state

  /**
   * @since 0.1.0
   */
  override val hostages: List<User>
    get() = this._hostages.values.toList()

  /**
   * @since 0.1.0
   */
  override val smallestTeam: LocalTeam
    get() = this._teams.values.minBy(LocalTeam::size)

  /**
   * @since 0.1.0
   */
  override val largestTeam: LocalTeam
    get() = this._teams.values.maxBy(LocalTeam::size)

  /**
   * @since 0.1.0
   */
  override val randomTeam: LocalTeam
    get() = this._teams.values.random()

  /**
   * @since 0.1.0
   */
  override val numberOfHostages: Int
    get() = this._hostages.size

  /**
   * @since 0.1.0
   */
  override val numberOfHostagesEnrolled: Int
    get() = this._hostages.size

  /**
   * @since 0.1.0
   */
  override val isTeamsSameSize: Boolean
    get() {
      val first = this._teams.values.firstOrNull()?.size ?: return false
      return this._teams.values.all {
        it.size == first
      }
    }

  /**
   * @since 0.1.0
   */
  override fun setAsInitialized() {
    this._state = GameStates.INITIALIZED
  }

  /**
   * @since 0.1.0
   */
  override fun setAsStarting() {
    this._state = GameStates.STARTING
  }

  /**
   * @since 0.1.0
   */
  override fun setAsRunning() {
    this._state = GameStates.RUNNING
  }

  /**
   * @since 0.1.0
   */
  override fun setAsStopping() {
    this._state = GameStates.STOPPING
  }

  /**
   * @since 0.1.0
   */
  private fun nextArena(): Arena {
    var arena = this._currentArena
    if (null != arena) {
      return arena
    }

    arena = this._arenas.values.random()
    this._currentArena = arena
    return arena
  }

  /**
   * @since 0.1.0
   */
  private fun shouldStart() {
    if (!this.isInitialized ||
      Configuration.MIN_TEAMMATES_SIZE > this.numberOfHostagesEnrolled) {
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
      team.name,
      team.colorInChat
    )

    // TODO precise
    team.health = arena.monumentPositions.size / 2

    val location = arena.getRevivalPosition(team)?.let {
      val world = arena.map?.world ?: return
      it.toLocation(world)
    }

    val player = teammate.user.performer.player!!
    this.fsf(teammate, location!!)

    player.scoreboard = bukkitTeamScoreboard
    bukkitTeam.addPlayer(player)
  }

  override fun findHostageByIdentifier(
    identifier: String
  ): User? {
    return this._hostages[UUID.fromString(identifier)]
  }

  /**
   * @since 0.1.0
   */
  override fun findTeamByHostage(
    hostage: User
  ): LocalTeam? {
    return this._teams.values.find { team: LocalTeam ->
      team.hasTeammate(hostage)
    }
  }

  override fun findTeammateByHostage(hostage: User): Teammate? {
    for (team: LocalTeam in this._teams.values) {
      for (teammate: Teammate in team.teammates) {
        if (teammate.user == hostage) {
          return teammate
        }
      }
    }
    return null
  }

  /**
   * Checks if the given [user] is in the game.
   */
  override fun isInGame(
    user: User
  ): Boolean {
    return this._hostages.contains(user.identifier)
  }

  /**
   * Checks if the given [user] is in any team.
   */
  override fun isInTeam(
    user: User
  ): Boolean {
    return this._teams.values.any {
      if (it is LocalTeam) {
        return@any it.hasTeammate(user)
      }

      return false
    }
  }

  /**
   * See documentation in [LocalGame.isInArena]
   *
   * @since 0.1.0
   */
  override fun isInArena(
    user: User
  ): Boolean {
    // Jeżeli obiekt gry nie ma stanu "RUNNING" lub ma stan "STOPPING" to
    // użytkownik nigdy nie będzie na arenie gry.
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
  override fun addHostage(
    user: User
  ): Boolean {
    if (this.isInGame(user)) {
      return false
    }

    this._hostages[user.identifier] = user
    ++this.spectators

    Debug.log {
      "$user user has been added as a hostage to the $this game."
    }

    return true
  }

  /**
   * @since 0.1.0
   */
  override fun addTeammate(
    team: LocalTeam,
    user: User
  ): Boolean {
    //
    val team: LocalTeam = this.findTeamByIdentifier(team.identifier) ?: return false

    //
    if (team.hasTeammate(user)) {
      return false
    }

    //
    val teammate = BaseTeammate(this, team, user)

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
  override fun removeHostage(
    user: User
  ): Boolean {
    val hostage: User = this._hostages[user.identifier] ?: return false
    this._hostages.remove(user.identifier)

    Debug.log {
      ""
    }

    val team = this.findTeamByHostage(hostage) ?: return true
    this.removeTeammate(team, hostage)
    return true
  }

  /**
   * @since 0.1.0
   */
  override fun removeTeammate(
    team: LocalTeam,
    user: User
  ): Boolean {
    //
    val team = this.findTeamByIdentifier(team.identifier) ?: return false

    if (!team.removeTeammate(user)) {
      return false
    }

    ++this.spectators
    return true
  }

  fun fsf(teammate: Teammate, spawn: Location) {
    val player = teammate.user.performer.player ?: throw IllegalStateException("Player not found")

    player.teleport(spawn)
    player.reset()

    teammate.currentProfession.equip(player, teammate.team.dyeColor)
    teammate.currentProfession.ability?.renewDelayed(player, true)
  }

  var timerTask: GameTimeUpdateTaskTimer? = null

  /**
   *
   */
  override fun start() {
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
    for (team: LocalTeam in signedTeams) {
      team.health = arena.getMonumentPositions(team).size
    }

    //
    //
    val teamPair = Pair(signedTeams.first(), signedTeams.last())

    for (team: LocalTeam in signedTeams) {

      val bukkitTeam = createServerTeam(
        bukkitTeamScoreboard,
        team.name,
        team.colorInChat
      )

      val location = arena.getRevivalPosition(team)?.let {
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
    this.setAsRunning()
  }

  /**
   * @since 0.1.0
   */
  override fun stop() {
    check(this.isRunning) {
      "Game cannot be stopped because it is not currently running."
    }

    //
    this.setAsStopping()

    if (this.timerTaskIdentifier >= 0) {
      cancelTask(this.timerTaskIdentifier)
      this.timerTaskIdentifier = -1
    }

    //
    this._teams.values.forEach {

      it.teammates.forEach {

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
      this._currentArena!!.map!!.unload()
      this._currentArena!!.monumentPositions.forEach {
        it.restore()
      }
      this._currentArena = null
    } catch (exception: Exception) {
      exception.printStackTrace()
    }

    this.spectators = this._hostages.size

    // Reinitializing
    this.setAsInitialized()
  }

  override fun toLocalGame(): LocalGame {
    return this
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
}