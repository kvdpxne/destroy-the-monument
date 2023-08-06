package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.user.User
import java.util.UUID

object GameManager {

  val games: MutableMap<UUID, Game>

  init {
    games = linkedMapOf()

    // TODO Delete in the future.
    UUID.fromString("4d966ed3-86b8-4acd-bb48-aed322cf14fe").let {
      games[it] = Game(it, "test").apply {
        addTeam(Team(DefaultTeamColor.BLUE, this))
        addTeam(Team(DefaultTeamColor.RED, this))

        val arena = ArenaManager.createArena("test_arena")!!
        addArena(arena)
      }
    }
  }

  /**
   * Tries to find a [Game] by [Game.identifier].
   */
  fun findByIdentifier(identifier: UUID): Game? {
    return games[identifier]
  }

  /**
   * Tries to find a [Game] by [Game.name].
   */
  fun findByName(name: String, ignoreCase: Boolean = true): Game? {
    return games.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

//  fun findGameByArenaName(name: String, ignoreCase: Boolean = true): Game? {
//    return games.values
//      .find { it.arenas?.name.equals(name, ignoreCase) }
//  }

  fun findGameWithUser(user: User): Game? {
    return games.values.find {
      it.isInGame(user)
    }
  }

  fun createGame(name: String): Boolean {
    if (null != findByName(name)) {
      return false
    }
    val identifier = UUID.randomUUID()
    games[identifier] = Game(identifier, name)
    return true
  }
}