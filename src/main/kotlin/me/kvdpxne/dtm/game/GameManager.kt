package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.data.GameDao
import me.kvdpxne.dtm.user.User

object GameManager {

  var games: MutableMap<UUID, Game> = mutableMapOf()
    private set

  init {
    // TODO Delete in the future.
    // Information about games should be loaded into memory only when it is
    // really needed and removed when it is no longer needed.
    GameDao.findAll().forEach {
      games[it.identifier] = it
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

  fun findByUser(user: User): Game? {
    return games.values.find {
      it.isInGame(user)
    }
  }

  fun createGame(name: String): Boolean {
    require(name.isNotBlank()) {
      "The name of the game must contain some characters and cannot be just" +
        "whitespace."
    }

    if (null != findByName(name)) {
      return false
    }
    val identifier = UUID.randomUUID()
    val game = Game(identifier, name)
    games[identifier] = game
    GameDao.insert(game)
    return true
  }
}