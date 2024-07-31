package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.DaoGame
import me.kvdpxne.dtm.data.DaoGameArena
import me.kvdpxne.dtm.data.DaoGameTeam

object GameService {

  fun findGames(): List<Game> {
    return DaoGame.findGames()
  }

  fun findGameArenaByGameIdentifier(
    identifier: String
  ): List<Arena> {
    return DaoGameArena.findGameArenaByGameIdentifier(identifier)
  }

  fun findGameTeamByGameIdentifier(
    identifier: String
  ): List<TeamIdentity> {
    return DaoGameTeam.findGameTeamByGameIdentifier(identifier)
  }

  fun insertGame(game: Game) {
    DaoGame.insertGame(game)
  }
}