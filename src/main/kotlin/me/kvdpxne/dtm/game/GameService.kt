package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.DaoGameArena
import me.kvdpxne.dtm.data.DaoGameTeam
import me.kvdpxne.dtm.game.temporary.Game

object GameService {

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

  }
}