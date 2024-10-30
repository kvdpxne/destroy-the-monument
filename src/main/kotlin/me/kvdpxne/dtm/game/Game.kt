package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.Nameable
import me.kvdpxne.dtm.team.Team

/**
 * Represents a game session within the plugin, defining the key components and
 * behaviors associated with the game, such as teams and arenas.
 *
 * @param T The type of team used in the game, inheriting from [Team].
 *
 * @since 0.1.0
 */
interface Game<T : Team> : Identifiable<UUID>, Nameable {

  /**
   * The collection of teams participating in the game.
   *
   * @since 0.1.0
   */
  val teams: Collection<T>

  /**
   * The collection of arenas available in the game.
   *
   * @since 0.1.0
   */
  val arenas: Collection<Arena>

  /**
   * The number of teams in the game.
   *
   * @since 0.1.0
   */
  val numberOfTeams: Int

  /**
   * The number of arenas in the game.
   *
   * @since 0.1.0
   */
  val numberOfArenas: Int

  /**
   * Indicates if the game is local. Default value is `false`.
   *
   * @return `true` if the game is local; `false` otherwise.
   * @since 0.1.0
   */
  val isLocal: Boolean
    get() = false

  /**
   * Checks if a specified team is part of the game.
   *
   * @param team The team to check.
   * @return `true` if the team is part of the game; `false` otherwise.
   * @since 0.1.0
   */
  fun hasTeam(team: T): Boolean

  /**
   * Checks if a specified arena is part of the game.
   *
   * @param arena The arena to check.
   * @return `true` if the arena is part of the game; `false` otherwise.
   * @since 0.1.0
   */
  fun hasArena(arena: Arena): Boolean

  /**
   * Finds a team by its unique identifier.
   *
   * @param identifier The unique identifier of the team.
   * @return The team if found, or `null` if no matching team exists.
   * @since 0.1.0
   */
  fun findTeamByIdentifier(identifier: UUID): T?

  /**
   * Finds an arena by its unique identifier.
   *
   * @param identifier The unique identifier of the arena.
   * @return The arena if found, or `null` if no matching arena exists.
   * @since 0.1.0
   */
  fun findArenaByIdentifier(identifier: UUID): Arena?

  /**
   * Converts the current game instance to a [LocalGame].
   *
   * @return A [LocalGame] representation of this game.
   * @since 0.1.0
   */
  fun toLocalGame(): LocalGame
}