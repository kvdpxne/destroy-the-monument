package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.listeners.netty.NettyListenerManager
import me.kvdpxne.dtm.scoreboard.createServerScoreboard
import me.kvdpxne.dtm.scoreboard.createServerTeam
import me.kvdpxne.dtm.scoreboard.initScoreboard
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancelTask
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipB
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.toLocation
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.team.TeammateImpl
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Bukkit
import org.bukkit.Location

/**
 * @param name
 * @param teams
 * @param arenas
 * @param identifier
 *
 * @since 0.1.0
 */
class LocalGameImpl(
  // @formatter:off
  name      : String,
  teams     : Map<UUID, LocalTeam>,
  arenas    : Map<UUID, Arena>,
  identifier: UUID
  // @formatter:on
) : GameImpl<LocalTeam>(
  name,
  name,
  teams,
  arenas,
  identifier
), LocalGame {

  /**
   * @since 0.1.0
   */
  private val _hostages: MutableMap<UUID, LocalUser> = mutableMapOf()

  private var _currentArena: Arena? = null

  private var _state: Int = GameStates.INITIALIZED

  /**
   * Number of users present in the game but not currently playing.
   *
   * @since 0.1.0
   */
  private var spectators: Int = 0

  @Volatile
  var timerTask: LocalGameTimerTask? = null

  /**
   * @since 0.1.0
   */
  override var timerTaskIdentifier: Int = -1

  /**
   * @since 0.1.0
   */
  override val currentArena: Arena?
    get() = this._currentArena

  /**
   * @since 0.1.0
   */
  override val state: Int
    get() = this._state

  /**
   * @since 0.1.0
   */
  override val hostages: List<LocalUser>
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
    get() = this._hostages.size - this.spectators

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
      Configuration.MIN_TEAMMATES_SIZE > this.numberOfHostagesEnrolled
    ) {
      return
    }

    GameStartAsyncTask(this)
      .runTaskTimerAsynchronously(DestroyTheMonument.instance, 10L, 20L)
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

    //
    val signedTeams = this.teams

    //
    //
    val teamPair = Pair(signedTeams.first(), signedTeams.last())

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

    player.scoreboard = bukkitTeamScoreboard
    bukkitTeam.addPlayer(player)

    NettyListenerManager.addPlayer(player)
  }

  /**
   * @since 0.1.0
   */
  override fun findHostageByIdentifier(
    identifier: UUID
  ): LocalUser? {
    return this._hostages[identifier]
  }

  /**
   * @since 0.1.0
   */
  override fun findTeamByHostage(
    hostage: LocalUser
  ): LocalTeam? {
    for (localTeam: LocalTeam in this._teams.values) {
      if (localTeam.hasTeammate(hostage)) {
        return localTeam
      }
    }
    return null
  }

  /**
   * @since 0.1.0
   */
  override fun findTeammateByHostage(
    hostage: LocalUser
  ): Teammate? {
    for (localTeam: LocalTeam in this._teams.values) {
      val teammate: Teammate? = localTeam.getTeammate(hostage)
      if (null != teammate) {
        return teammate
      }
    }
    return null
  }

  /**
   * Checks if the given [user] is in the game.
   */
  override fun isInGame(
    user: LocalUser
  ): Boolean {
    return this._hostages.contains(user.identifier)
  }

  /**
   * Checks if the given [user] is in any team.
   */
  override fun isInTeam(
    user: LocalUser
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
    user: LocalUser
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
    user: LocalUser
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
    user: LocalUser
  ): Boolean {
    // Przekazany obiekt użytkownika nie zostanie dodany do przekazanego
    // obiektu drużyny, jeżeli jest już do niej przypisany.
    if (team.hasTeammate(user)) {
      return false
    }

    //
    val teammate: Teammate = TeammateImpl(this, team, user)

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
    user: LocalUser
  ): Boolean {
    val hostage: LocalUser = this._hostages[user.identifier] ?: return false
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
    user: LocalUser
  ): Boolean {
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
    this.timerTask = LocalGameTimerTask(this)

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

        NettyListenerManager.addPlayer(player)
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

        NettyListenerManager.removePlayer(player)

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
   * Alias for [User.sendConfiguredMessage]
   */
  override fun sendMessage(
    message: String
  ) {
    if (this._hostages.isEmpty()) {
      return
    }

    this._hostages.values.forEach { user: LocalUser ->
      user.sendMessage(message)
    }
  }

  /**
   * Alias for [User.sendConfiguredMessage]
   */
  override fun sendMessage(
    message: () -> String
  ) {
    if (this._hostages.isEmpty()) {
      return
    }

    val body = message()
    this._hostages.values.forEach { user: LocalUser ->
      user.sendMessage(body)
    }
  }

  /**
   * Alias for [User.sendConfiguredMessages]
   */
  override fun sendMessages(
    vararg messages: String
  ) {
    if (this._hostages.isEmpty() || messages.isEmpty()) {
      return
    }

    this._hostages.values.forEach { user: LocalUser ->
      user.sendMessages(*messages)
    }
  }

  override fun toString(): String {
    return "LocalGame{" +
      "name=\"${this.name}\", " +
      "displayName=\"${this.displayName}\", " +
      "teams=\"${this._teams.values}\", " +
      "arenas=\"${this._arenas.values}\", " +
      "currentArena=\"${this.currentArena}\", " +
      "hostages=\"${this._hostages.values}\", " +
      "state=\"${this.state}\", " +
      "identifier=\"${this.identifier}\"" +
      "}"
  }
}