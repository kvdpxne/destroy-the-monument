package me.kvdpxne.dtm.game

import java.util.UUID
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.runInterruptible
import me.kvdpxne.dtm.data.GameArenasDao
import me.kvdpxne.dtm.data.GameDao
import me.kvdpxne.dtm.data.GameTeamsDao

/**
 * @since 0.1.0
 */
object GameService {

  /**
   * @since 0.1.0
   */
  fun findGames(): List<Game<Team>> {
    return runBlocking {
      GameDao.findGames()
    }
  }

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  fun findGameByIdentifier(
    identifier: UUID
  ): Game<Team>? {
    return runBlocking {
      GameDao.findGameByIdentifier(identifier)
    }
  }

  /**
   * @param name
   * @param ignoreCase
   *
   * @since 0.1.0
   */
  fun findGameByName(
    name: String,
    ignoreCase: Boolean = true
  ): Game<Team>? {
    return runBlocking {
      GameDao.findGameByName(name, ignoreCase)
    }
  }

  /**
   * @since 0.1.0
   */
  fun insertGame(
    game: Game<Team>
  ) {
    runBlocking {
      GameDao.insertGame(game)
    }
  }

  fun updateGameArena(game: Game<Team>, arena: Arena) {
    runBlocking {
      GameArenasDao.insertGameArena(game, arena)
    }
  }

  fun insertGameTeam(game: Game<Team>, team: Team) {
    runBlocking {
      GameTeamsDao.insertGameTeam(game, team)
    }
  }

  fun deleteGameTeam(game: Game<Team>, team: Team) {
    runBlocking {
      GameTeamsDao.deleteGameTeam(game, team)
    }
  }
}