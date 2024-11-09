package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.AbstractIdentifiable
import me.kvdpxne.dtm.shared.ArenaUuid
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.shared.TeamUuid
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.team.Team

/**
 * Implementation of the [Game] interface that holds teams and arenas.
 *
 * @param name The name of the game.
 * @param displayName The display name of the game.
 * @param teams A map of teams associated with their [UUID]s.
 * @param arenas A map of arenas associated with their [UUID]s.
 * @param identifier A unique identifier for the game.
 *
 * @since 0.1.0
 */
open class GameImpl<T : Team>(
  // @formatter:off
  override val name       : String,
  override val displayName: String,
               teams      : Map<TeamUuid, T>      = mutableMapOf(),
               arenas     : Map<ArenaUuid, Arena> = mutableMapOf(),
               identifier : GameUuid              = UUID.randomUUID()
  // @formatter:on
) : AbstractIdentifiable<GameUuid>(identifier), Game<T> {

  /**
   * A mutable map holding the teams in the game.
   *
   * @since 0.1.0
   */
  protected val _teams: MutableMap<TeamUuid, T> = teams.toMutableMap()

  /**
   * A mutable map holding the arenas in the game.
   *
   * @since 0.1.0
   */
  protected val _arenas: MutableMap<ArenaUuid, Arena> = arenas.toMutableMap()

  override val teams: Collection<T>
    get() = this._teams.values.toList()

  override val arenas: Collection<Arena>
    get() = this._arenas.values.toList()

  override val numberOfTeams: Int
    get() = this._teams.size

  override val numberOfArenas: Int
    get() = this._arenas.size

  override fun hasTeam(team: T): Boolean {
    return this._teams.containsValue(team)
  }

  override fun hasArena(arena: Arena): Boolean {
    return this._arenas.containsValue(arena)
  }

  override fun findTeamByIdentifier(identifier: TeamUuid): T? {
    return this._teams[identifier]
  }

  override fun findArenaByIdentifier(identifier: ArenaUuid): Arena? {
    return this._arenas[identifier]
  }

  /**
   * Adds a team to the game.
   *
   * @param team The team to add.
   * @return `true` if the team was added successfully; `false` if the tea
   *         already exists.
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
      "Team ${team.name} added to the game."
    }
    return true
  }

  /**
   * Adds an arena to the game.
   *
   * @param arena The arena to add.
   * @return `true` if the arena was added successfully; `false` if the arena
   *         already exists.
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

    Debug.log {
      "Arena ${arena.name} added to the game."
    }
    return true
  }

  override fun toLocalGame(): LocalGame {
    return LocalGameImpl(
      //
      identifier = this.identifier,
      name = this.name,

      //
      teams = this._teams.mapValues { (_: TeamUuid, team: Team) ->
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
    return StylishToStringBuilder()
      .begin("Game")
      .add("name", this.name)
      .add("displayName", this.displayName)
      .add("teams", this._teams.values)
      .add("arenas", this._arenas.values)
      .add("identifier", this.identifier)
      .build()
  }
}