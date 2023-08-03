package me.kvdpxne.dtm.game

import java.util.*

object GameManager {

  val games: MutableMap<UUID, Game>

  init {
    games = linkedMapOf()

    // TODO Delete in the future.
    UUID.fromString("4d966ed3-86b8-4acd-bb48-aed322cf14fe").let {
      games[it] = Game(it, "test").apply {
        addTeam(Team(DefaultTeamColor.BLUE, this))
        addTeam(Team(DefaultTeamColor.RED, this))
      }
    }
  }

  fun findGameByIdentifier(identifier: UUID): Game? {
    return games[identifier]
  }

  fun findGameByName(name: String): Game? {
    return games.values.find { it.name.equals(name, true) }
  }

  fun findGameByArenaName(name: String, ignoreCase: Boolean = true): Game? {
    return games.values
      .find { it.arena?.name.equals(name, ignoreCase) }
  }
}