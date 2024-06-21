package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

// Usage: /dtm CreateGame <GAME_NAME>
fun createCreateGameCommand(): Command = CommandBuilder()
  .name("createGame")
  .parent("dtm")
  .handler<UserPerformer> { performer, parameter ->
    if (1 > parameter.length()) {
      performer.sendMessage("Usage: /dtm CreateGame <GAME_NAME>")
      return@handler
    }

    val gameName = parameter.asText()
    GameManager.createGame(gameName)
    performer.sendMessage("Success")
  }
  .build()