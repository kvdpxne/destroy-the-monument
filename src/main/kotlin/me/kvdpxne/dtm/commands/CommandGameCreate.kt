package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.game.BaseGame
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.user.UserPerformer

fun createGameCreateCommand(): Command {
  // Usage: /dtm game create <GAME_NAME>
  return CommandBuilder()
    .name("create")
    .parameter(
      ParameterBuilder<String>()
        .name("game_name")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .build()
    )
    .handler<UserPerformer> { performer, arguments ->
      val gameName = arguments.asText()
      val game = BaseGame<Team>(gameName, gameName)

      GameService.insertGame(game)
      performer.sendMessage("Success")
    }
    .build()
}