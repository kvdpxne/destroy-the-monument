package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid

/**
 * @param name
 * @param displayName
 * @param teams
 * @param arenas
 * @param identifier
 *
 * @since 0.1.0
 */
open class BaseGame<T : Team>(
  // @formatter:off
  override val name       : String,
  override val displayName: String,
               teams      : Map<String, T>     = mutableMapOf(),
               arenas     : Map<String, Arena> = mutableMapOf(),
               identifier : String             = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier), Game<T> {

  /**
   * @since 0.1.0
   */
  protected val _teams: Map<String, T> = teams

  /**
   * @since 0.1.0
   */
  protected val _arenas: Map<String, Arena> = arenas

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

  override fun findTeamByIdentifier(identifier: String): T? {
    require(identifier.isNotBlank()) {
      "The given key must not be blank."
    }

    return this._teams[identifier]
  }

  override fun findArenaByIdentifier(identifier: String): Arena? {
    require(identifier.isNotBlank()) {
      "The given key must not be blank."
    }

    return this._arenas[identifier]
  }

  /**
   * @since 0.1.0
   */
  override fun toLocalGame(): LocalGame {
    return BaseLocalGame(
      //
      identifier = this.identifier,
      name = this.name,

      //
      //
      hostages = mutableMapOf(),

      //
      teams = this._teams.mapValues { (_: String, team: Team) ->
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