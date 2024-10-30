package me.kvdpxne.dtm.arena

import java.util.UUID
import me.kvdpxne.dtm.position.MonumentPosition
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.team.Team

/**
 * Represents an arena in the game, providing functionalities related to team
 * revival and monument positions.
 *
 * The [Arena] interface extends [Identifiable] to ensure each arena has
 * a unique identifier.
 *
 * @since 0.1.0
 */
interface Arena : Identifiable<UUID> {

  /**
   * The name of the arena.
   *
   * @since 0.1.0
   */
  val name: String

  /**
   * A list of revival positions available in the arena for teams.
   *
   * @since 0.1.0
   */
  val revivalPositions: List<RevivalPosition<out Team>>

  /**
   * A list of monument positions available in the arena for teams.
   *
   * @since 0.1.0
   */
  val monumentPositions: List<MonumentPosition<out Team>>

  /**
   * The map associated with the arena, if available.
   *
   * @since 0.1.0
   */
  val map: ArenaMap?

  /**
   * Retrieves the revival position for a specified team.
   *
   * @param team The team for which to get the revival position.
   * @return The revival position associated with the team, or `null` if
   *         not found.
   *
   * @since 0.1.0
   */
  fun getRevivalPosition(
    team: Team
  ): RevivalPosition<out Team>?

  /**
   * Retrieves a list of monument positions associated with a specified team.
   *
   * @param team The team for which to get monument positions.
   * @return A list of monument positions for the team.
   *
   * @since 0.1.0
   */
  fun getMonumentPositions(
    team: Team
  ): List<MonumentPosition<out Team>>

  /**
   * Retrieves a specific monument position based on coordinates.
   *
   * @param x The x-coordinate of the monument position.
   * @param y The y-coordinate of the monument position.
   * @param z The z-coordinate of the monument position.
   * @return The monument position at the specified coordinates, or `null` if
   *         not found.
   *
   * @since 0.1.0
   */
  fun getMonumentPosition(
    x: Int,
    y: Int,
    z: Int
  ): MonumentPosition<Team>?

  /**
   * Retrieves a monument position using a [BlockPosition] object.
   *
   * @param position The block position to retrieve the monument from.
   * @return The monument position at the specified block position, or `null`
   *         if not found.
   *
   * @since 0.1.0
   */
  fun getMonumentPosition(
    position: BlockPosition
  ): MonumentPosition<Team>? {
    return this.getMonumentPosition(
      position.x,
      position.y,
      position.z
    )
  }
}