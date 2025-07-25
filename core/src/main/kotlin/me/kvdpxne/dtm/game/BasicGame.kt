package me.kvdpxne.dtm.game

import java.util.Collections
import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.capabilities.Identifiable
import me.kvdpxne.dtm.capabilities.Nameable
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.team.Team
import org.jetbrains.annotations.Range
import org.jetbrains.annotations.UnmodifiableView

open class BasicGame<T : Team>(
  // @formatter:off
  internal val _uid     : UUID,
  internal val _name    : String,
  internal val _settings: GameSettings,
  internal val _arenas  : Map<UUID, Arena>,
  internal val _teams   : Map<UUID, T>
  // @formatter:on
) : Identifiable<UUID>, Nameable, Game {

  override fun getIdentifier(): UUID {
    return this._uid
  }

  override fun getName(): String {
    return this._name.lowercase(Locale.US)
  }

  override fun getSettings(): GameSettings {
    return this._settings
  }

  override fun getArenas(): @UnmodifiableView Collection<Arena> {
    return Collections.unmodifiableCollection(this._arenas.values)
  }

  override fun getTeams(): @UnmodifiableView Collection<Team> {
    return Collections.unmodifiableCollection(this._teams.values)
  }

  override fun getNumberOfArenas(): @Range(from = 0x0, to = 0x7fffffff) Int {
    return this._arenas.size
  }

  override fun getNumberOfTeams(): @Range(from = 0x0, to = 0x7fffffff) Int {
    return this._teams.size
  }

  override fun hasArena(
    arena: Arena
  ): Boolean {
    return this._arenas.containsValue(arena)
  }

  override fun hasArenaByIdentifier(
    identifier: UUID
  ): Boolean {
    return this._arenas.any { entry: Map.Entry<UUID, Arena> ->
      entry.value.getIdentifier() == identifier
    }
  }

  override fun hasTeam(
    team: Team
  ): Boolean {
    return this._teams.containsValue(team)
  }

  override fun hasTeamByIdentifier(
    identifier: UUID
  ): Boolean {
    return this._teams.any { entry: Map.Entry<UUID, Team> ->
      entry.value.getIdentifier() == identifier
    }
  }

  override fun toLocal(): LocalGame? {
    if (this is LocalGame?) {
      return this
    }

    return try {
      BasicLocalGame(
        this._uid,
        this.name,
        this.settings,
        this._arenas,
        this._teams.mapValues { (_, v) -> v.toLocalTeam() }.toMap()
      )
    } catch (_: Throwable) {
      null
    }
  }
}