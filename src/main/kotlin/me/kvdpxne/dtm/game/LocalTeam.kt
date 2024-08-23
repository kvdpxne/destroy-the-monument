package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.user.User

interface LocalTeam : Communicative, Team {

  /**
   * Collection of teammates belonging to the team.
   *
   * @since 0.1.0
   */
  val teammates: List<Teammate>

  /**
   * @since 0.1.0
   */
  var health: Int

  /**
   * Returns an unsigned integer representing the total number of teammates
   * on the current team.
   *
   * @return The number of teammates on the team.
   * @since 0.1
   */
  val size: Int

  /**
   * @since 0.1.0
   */
  fun hasTeammate(
    user: User
  ): Boolean

  /**
   * @since 0.1.0
   */
  fun getTeammate(
    user: User
  ): Teammate?

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to add a teammate to a team, use the [Game.addTeammate]
   * method.
   */
  fun addTeammate(
    teammate: Teammate
  ): Boolean

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to remove a teammate from a team, use the
   * [Game.removeTeammate] method.
   */
  fun removeTeammate(
    user: User
  ): Boolean

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to remove a teammate from a team, use the
   * [Game.removeTeammate] method.
   */
  fun removeTeammate(
    teammate: Teammate
  ): Boolean

  /**
   * @since 0.1.0
   */
  fun removeTeammates()

  /**
   * @since 0.1.0
   */
  fun injure(): Boolean
}