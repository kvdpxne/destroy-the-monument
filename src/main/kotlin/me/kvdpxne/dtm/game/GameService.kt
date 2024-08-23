package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.DaoGame
import me.kvdpxne.dtm.data.DaoGameArena
import me.kvdpxne.dtm.data.DaoGameTeam
import me.kvdpxne.dtm.game.Game

/**
 * @since 0.1.0
 */
object GameService {

  /**
   * @since 0.1.0
   */
  fun findGames(): List<Game<Team>> {
    return DaoGame.findGames()
  }

  /**
   * @since 0.1.0
   */
  fun findGameArenasByGameIdentifier(
    identifier: String
  ): List<Arena> {
    return DaoGameArena.findGameArenaByGameIdentifier(identifier)
  }

  /**
   * @since 0.1.0
   */
  fun findGameTeamsByGameIdentifier(
    identifier: String
  ): List<Team> {
    return DaoGameTeam.findGameTeamByGameIdentifier(identifier)
  }

  /**
   * @since 0.1.0
   */
  fun insertGame(game: Game<*>) {
    DaoGame.insertGame(game)
  }
}