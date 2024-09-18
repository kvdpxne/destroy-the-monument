package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.GameImpl
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.game.Team

fun createGameCreateCommand(): Command<Performer> {
  // Usage: /dtm game create <GAME_NAME>
  return CommandBuilder.begin<Performer>("create")
    .parameter(
      Parameters.gameNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      val gameName: String = parameters[0] as String
      val game: Game<Team> = GameImpl(gameName, gameName)

      GameService.insertGame(game)
      performer.sendMessage("Success")
    }
    .build()
}