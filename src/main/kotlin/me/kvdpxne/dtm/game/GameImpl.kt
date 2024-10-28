package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.team.Team

/**
 * @param name
 * @param displayName
 * @param teams
 * @param arenas
 * @param identifier
 *
 * @since 0.1.0
 */
open class GameImpl<T : Team>(
  // @formatter:off
  override val name       : String,
  override val displayName: String,
               teams      : Map<UUID, T>     = mutableMapOf(),
               arenas     : Map<UUID, Arena> = mutableMapOf(),
               identifier : UUID             = UUID.randomUUID()
  // @formatter:on
) : AbstractIdentifiable<UUID>(identifier), Game<T> {

  /**
   * @since 0.1.0
   */
  protected val _teams: MutableMap<UUID, T> = teams.toMutableMap()

  /**
   * @since 0.1.0
   */
  protected val _arenas: MutableMap<UUID, Arena> = arenas.toMutableMap()

  /**
   * @since 0.1.0
   */
  override val teams: Collection<T>
    get() = this._teams.values.toList()

  /**
   * @since 0.1.0
   */
  override val arenas: Collection<Arena>
    get() = this._arenas.values.toList()

  /**
   * @since 0.1.0
   */
  override val numberOfTeams: Int
    get() = this._teams.size

  /**
   * @since 0.1.0
   */
  override val numberOfArenas: Int
    get() = this._arenas.size

  /**
   * @since 0.1.0
   */
  override fun hasTeam(
    team: T
  ): Boolean {
    return this._teams.containsValue(team)
  }

  /**
   * @since 0.1.0
   */
  override fun hasArena(
    arena: Arena
  ): Boolean {
    return this._arenas.containsValue(arena)
  }

  override fun findTeamByIdentifier(
    identifier: UUID
  ): T? {
    return this._teams[identifier]
  }

  override fun findArenaByIdentifier(
    identifier: UUID
  ): Arena? {
    return this._arenas[identifier]
  }

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  internal fun addTeam(
    team: Team
  ): Boolean {
    if (team.identifier in this._teams) {
      return false
    }

    @Suppress("UNCHECKED_CAST")
    this._teams[team.identifier] = team as T

    Debug.log {
      ""
    }
    return true
  }

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  internal fun addArena(
    arena: Arena
  ): Boolean {
    if (arena.identifier in this._teams) {
      return false
    }

    this._arenas[arena.identifier] = arena
    return true
  }

  /**
   * @since 0.1.0
   */
  override fun toLocalGame(): LocalGame {
    return LocalGameImpl(
      //
      identifier = this.identifier,
      name = this.name,

      //
      teams = this._teams.mapValues { (_: UUID, team: Team) ->
        team.toLocalTeam()
      }.toMap(),

      //
      arenas = this._arenas.toMap()
    )
  }

  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (other !is Game<*>) {
      return false
    }

    return super.equals(other)
  }

  override fun hashCode(): Int {
    return super.hashCode()
  }

  override fun toString(): String {
    return "Game{" +
      "name=\"${this.name}\", " +
      "displayName=\"${this.displayName}\", " +
      "teams=\"${this._teams.values}\", " +
      "arenas=\"${this._arenas.values}\", " +
      "identifier=\"${this.identifier}\"" +
      "}"
  }
}