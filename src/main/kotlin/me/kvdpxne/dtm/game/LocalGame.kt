package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.Communicative
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.user.LocalUser

/**
 * Represents a local game instance that allows for player interaction and
 * communication.
 *
 * It extends the [Game] interface with specific functionalities tailored for
 * local gameplay.
 *
 * @since 0.1.0
 */
interface LocalGame : Game<LocalTeam>, Communicative {

  /**
   * A list of hostages currently in the game.
   *
   * @since 0.1.0
   */
  val hostages: List<LocalUser>

  /**
   * The smallest team in the game.
   *
   * @since 0.1.0
   */
  val smallestTeam: LocalTeam

  /**
   * The largest team in the game.
   *
   * @since 0.1.0
   */
  val largestTeam: LocalTeam

  /**
   * A randomly selected team from the game.
   *
   * @since 0.1.0
   */
  val randomTeam: LocalTeam

  /**
   * The current arena where the game is taking place, if any.
   *
   * @since 0.1.0
   */
  val currentArena: Arena?

  /**
   * The current state of the game represented by an integer.
   *
   * @since 0.1.0
   */
  val state: Int

  /**
   * The total number of hostages in the game.
   *
   * @since 0.1.0
   */
  val numberOfHostages: Int

  /**
   * The number of hostages enrolled in the game.
   *
   * @since 0.1.0
   */
  val numberOfHostagesEnrolled: Int

  /**
   * Indicates if the game has been initialized and is ready to start.
   *
   * @since 0.1.0
   */
  val isInitialized: Boolean
    get() = GameStates.INITIALIZED == this.state

  /**
   * Indicates if the game is in the process of starting.
   *
   * @since 0.1.0
   */
  val isStarting: Boolean
    get() = GameStates.STARTING == this.state

  /**
   * Indicates if the game is currently running.
   *
   * @since 0.1.0
   */
  val isRunning: Boolean
    get() = GameStates.RUNNING == this.state

  /**
   * Indicates if the game is in the process of ending.
   *
   * @since 0.1.0
   */
  val isEnding: Boolean
    get() = GameStates.ENDING == this.state

  /**
   * Indicates if the game is in the process of stopping.
   *
   * @since 0.1.0
   */
  val isStopping: Boolean
    get() = GameStates.STOPPING == this.state

  /**
   * Indicates if all teams in the game are of the same size.
   *
   * @since 0.1.0
   */
  val isTeamsSameSize: Boolean

  /**
   * An identifier for the timer task associated with the game.
   *
   * @since 0.1.0
   */
  var timerTaskIdentifier: Int

  /**
   * Sets the game as initialized.
   *
   * @since 0.1.0
   */
  fun setAsInitialized()

  /**
   * Sets the game as starting.
   *
   * @since 0.1.0
   */
  fun setAsStarting()

  /**
   * Sets the game as running.
   *
   * @since 0.1.0
   */
  fun setAsRunning()

  /**
   * Sets the game as stopping.
   *
   * @since 0.1.0
   */
  fun setAsStopping()

  /**
   * Finds a hostage by their unique identifier.
   *
   * @param identifier The [UUID] of the hostage.
   * @return The corresponding [LocalUser] if found, or `null` if not found.
   * @since 0.1.0
   */
  fun findHostageByIdentifier(
    identifier: UUID
  ): LocalUser?

  /**
   * Finds the team associated with a specific hostage.
   *
   * @param hostage The hostage to find the team for.
   * @return The corresponding [LocalTeam] if found, or `null` if not found.
   * @since 0.1.0
   */
  fun findTeamByHostage(
    hostage: LocalUser
  ): LocalTeam?

  /**
   * Finds the teammate associated with a specific hostage.
   *
   * @param hostage The hostage to find the teammate for.
   * @return The corresponding [Teammate] if found, or `null` if not found.
   * @since 0.1.0
   */
  fun findTeammateByHostage(
    hostage: LocalUser
  ): Teammate?

  /**
   * Finds the team and teammate associated with a specific hostage.
   *
   * @param hostage The hostage to find the team and teammate for.
   * @return A pair containing the [LocalTeam] and [Teammate] if found, or
   *         `null` if not found.
   * @since 0.1.0
   */
  fun findTeammateTeamByHostage(
    hostage: LocalUser
  ): Pair<LocalTeam, Teammate>?

  /**
   * Checks if a user is currently in the game.
   *
   * @param user The user to check.
   * @return `true` if the user is in the game; `false` otherwise.
   * @since 0.1.0
   */
  fun isInGame(
    user: LocalUser
  ): Boolean

  /**
   * Checks if a user is currently in a team.
   *
   * @param user The user to check.
   * @return `true` if the user is in a team; `false` otherwise.
   * @since 0.1.0
   */
  fun isInTeam(
    user: LocalUser
  ): Boolean

  /**
   * Checks if a user is currently in an arena.
   *
   * @param user The user to check.
   * @return `true` if the user is in an arena; `false` otherwise.
   * @since 0.1.0
   */
  fun isInArena(
    user: LocalUser
  ): Boolean

  /**
   * Adds a user as a hostage to the game.
   *
   * @param user The user to be added as a hostage.
   * @return `true` if the user was successfully added; `false` otherwise.
   * @since 0.1.0
   */
  fun addHostage(
    user: LocalUser
  ): Boolean

  /**
   * Adds a user as a teammate to a specified team.
   *
   * @param team The team to which the user will be added.
   * @param user The user to be added as a teammate.
   * @return `true` if the user was successfully added; `false` otherwise.
   * @since 0.1.0
   */
  fun addTeammate(
    team: LocalTeam,
    user: LocalUser
  ): Boolean

  /**
   * Removes a user from the hostages in the game.
   *
   * @param user The user to be removed.
   * @return `true` if the user was successfully removed; `false` otherwise.
   * @since 0.1.0
   */
  fun removeHostage(
    user: LocalUser
  ): Boolean

  /**
   * Removes a user from a specified team.
   *
   * @param team The team from which the user will be removed.
   * @param user The user to be removed.
   * @return `true` if the user was successfully removed; `false` otherwise.
   * @since 0.1.0
   */
  fun removeTeammate(
    team: LocalTeam,
    user: LocalUser
  ): Boolean

  /**
   * Starts the game, transitioning it to the running state.
   *
   * @since 0.1.0
   */
  fun start()

  /**
   * Stops the game, transitioning it to the stopping state.
   *
   * @since 0.1.0
   */
  fun stop()
}