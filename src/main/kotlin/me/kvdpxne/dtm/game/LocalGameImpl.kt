package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.scoreboard.createServerScoreboard
import me.kvdpxne.dtm.scoreboard.createServerTeam
import me.kvdpxne.dtm.scoreboard.initScoreboard
import me.kvdpxne.dtm.shared.ArenaUuid
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.shared.TeamUuid
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.player.equipB
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.shared.task.cancelTask
import me.kvdpxne.dtm.shared.text.toSingleLines
import me.kvdpxne.dtm.shared.world.toLocation
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.team.TeammateImpl
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.message.MessageBuilder
import me.kvdpxne.dtm.translation.message.MessageKeys
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
  teams     : Map<TeamUuid, LocalTeam>,
  arenas    : Map<ArenaUuid, Arena>,
  identifier: GameUuid
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
  private val _hostages: MutableMap<PlayerUuid, LocalUser> = mutableMapOf()

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

  override fun setAsEnding() {
    this._state = GameStates.ENDING
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
      GeneralConfiguration.MIN_TEAMMATES_SIZE > this.numberOfHostagesEnrolled
    ) {
      return
    }

    this.setAsStarting()
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
  }

  /**
   * @since 0.1.0
   */
  override fun findHostageByIdentifier(
    identifier: PlayerUuid
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

  override fun findTeammateTeamByHostage(
    hostage: LocalUser
  ): Pair<LocalTeam, Teammate>? {
    for (localTeam: LocalTeam in this._teams.values) {
      val teammate: Teammate? = localTeam.getTeammate(hostage)
      if (null != teammate) {
        return Pair(localTeam, teammate)
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
   * Increases the count of spectators by one.
   *
   * Logs the change in the spectators' count from the old value to the
   * new value.
   *
   * @since 0.1.0
   */
  private fun increaseSpectators() {
    val oldValue: Int = this.spectators
    ++this.spectators

    Debug.log {
      "Spectators increased: $oldValue -> ${this.spectators}"
    }
  }

  /**
   * Decreases the count of spectators by one if it’s above zero.
   *
   * Logs a warning if the spectators' count is already at zero, and logs the
   * change in count when successfully decreased.
   *
   * @since 0.1.0
   */
  private fun decreaseSpectators() {
    val oldValue: Int = this.spectators
    if (0 > oldValue - 1) {
      Debug.log {
        "Cannot decrease spectators: already at minimum (0)"
      }
      return
    }

    --this.spectators
    Debug.log {
      "Spectators decreased: $oldValue -> ${this.spectators}"
    }
  }

  override fun addHostage(
    user: LocalUser
  ): Boolean {
    if (this.isInGame(user)) {
      return false
    }

    this._hostages[user.identifier] = user
    this.increaseSpectators()

    Debug.log {
      "${user.name} user has been added to the ${this.name} game."
    }

    return true
  }

  private fun createAndAddTeammate(
    user: LocalUser,
    team: LocalTeam
  ): Teammate {
    //
    val teammate: Teammate = TeammateImpl(this, team, user)

    //
    if (!team.addTeammate(teammate)) {
      throw IllegalStateException("")
    }

    return teammate
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

    for (presentTeam: LocalTeam in this._teams.values) {
      // Jeżeli podany użytkownik, który ma być dodany do podanej drużyny, a
      // istnieje już w innej drużynie, to zostanie z niej usunięty.
      if (!presentTeam.removeTeammate(user)) {
        continue
      }

      // Jeżeli podany użytkownik zostanie usunięty z drużyny, to liczba
      // spektatorów zostanie zwiększona o 1.
      this.increaseSpectators()
    }

    //
    val teammate: Teammate = this.createAndAddTeammate(user, team)

    // Jeżeli podany użytkownik został pomyślnie dodany do podanej drużyny, to
    // liczba spektatorów zostanie zmniejszona o 1.
    this.decreaseSpectators()

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
    this.decreaseSpectators()

    Debug.log {
      "${user.name} user has been removed from the ${this.name} game."
    }

    this.findTeamByHostage(hostage)?.removeTeammate(user)
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

    this.increaseSpectators()
    return true
  }

  override fun relocateTeammateToTeam(
    teammate: Teammate,
    to: LocalTeam
  ): Boolean {
    val from: LocalTeam = teammate.team
    if (from == to || !from.hasTeammate(teammate) || to.hasTeammate(teammate)) {
      return false
    }

    from.removeTeammate(teammate)

    val user: LocalUser = teammate.user
    this.createAndAddTeammate(user, to)

    Debug.log {
      """
        User ${user.name} has been relocated from the ${from.name} team${" "}
        to the ${to.name} team.
      """.toSingleLines()
    }

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

    ArenaManager.addArena(arena)

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
    check(this.isRunning || this.isEnding) {
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
      ArenaManager.removeArena(this._currentArena!!)

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

  override fun constructMessage(messageKey: MessageKeys): MessageBuilder {
    return TranslationService.chains()
      .receivers(this._hostages.values.map { it.performer })
      .message(messageKey)
  }

  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("LocalGame")
      .add("name", this.name)
      .add("displayName", this.displayName)
      .add("teams", this._teams.values)
      .add("arenas", this._arenas.values)
      .add("currentArena", this._currentArena)
      .add("hostages", this._hostages.values)
      .add("state", this._state)
      .add("identifier", this.identifier)
      .build()
  }
}