package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.temporary.Game
import me.kvdpxne.dtm.user.UserPerformer

fun createGameCreateCommand(): Command {
  // Usage: /dtm game create <GAME_NAME>
  return CommandBuilder()
    .name("create")
    .handler<UserPerformer> { performer, arguments ->
      val gameName = arguments.asText()
      val game = Game(gameName)

      GameManager.createGame(game)
      performer.sendMessage("Success")
    }
    .build()
}