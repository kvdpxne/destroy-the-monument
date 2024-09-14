package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.user.User

/**
 * @since 0.1.0
 */
interface LocalGame : Game<LocalTeam>, Communicative {

  /**
   * @since 0.1.0
   */
  val hostages: List<User>

  /**
   * @since 0.1.0
   */
  val smallestTeam: LocalTeam

  /**
   * @since 0.1.0
   */
  val largestTeam: LocalTeam

  /**
   * @since 0.1.0
   */
  val randomTeam: LocalTeam

  /**
   * @since 0.1.0
   */
  val currentArena: Arena?

  /**
   * @since 0.1.0
   */
  val state: Int

  /**
   * @since 0.1.0
   */
  val numberOfHostages: Int

  /**
   * @since 0.1.0
   */
  val numberOfHostagesEnrolled: Int

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
   * @since 0.1.0
   */
  val isTeamsSameSize: Boolean

  var timerTaskIdentifier: Int

  /**
   * @since 0.1.0
   */
  fun setAsInitialized()

  /**
   * @since 0.1.0
   */
  fun setAsStarting()

  /**
   * @since 0.1.0
   */
  fun setAsRunning()

  /**
   * @since 0.1.0
   */
  fun setAsStopping()

  /**
   * @since 0.1.0
   */
  fun findHostageByIdentifier(
    identifier: String
  ): User?

  /**
   * @since 0.1.0
   */
  fun findTeamByHostage(
    hostage: User
  ): LocalTeam?

  /**
   * @since 0.1.0
   */
  fun findTeammateByHostage(
    hostage: User
  ): Teammate?

  /**
   * @since 0.1.0
   */
  fun isInGame(user: User): Boolean

  /**
   * @since 0.1.0
   */
  fun isInTeam(user: User): Boolean

  /**
   * @since 0.1.0
   */
  fun isInArena(user: User): Boolean

  /**
   * @since 0.1.0
   */
  fun addHostage(user: User): Boolean

  /**
   * @since 0.1.0
   */
  fun addTeammate(team: LocalTeam, user: User): Boolean

  /**
   * @since 0.1.0
   */
  fun removeHostage(user: User): Boolean

  /**
   * @since 0.1.0
   */
  fun removeTeammate(team: LocalTeam, user: User): Boolean

//  /**
//   * @since 0.1.0
//   */
//  fun removeTeammate(teammate: Teammate): Boolean

  /**
   * @since 0.1.0
   */
  fun start()

  /**
   * @since 0.1.0
   */
  fun stop()
}