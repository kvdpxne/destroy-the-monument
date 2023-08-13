package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

object CreateGameCommand : Executor<UserPerformer> {

  // Usage: /dtm CreateGame <GAME_NAME>
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (1 > parameter.length()) {
      performer.sendMessage("Usage: /dtm CreateGame <GAME_NAME>")
      return
    }

    val gameName = parameter.asText()
    GameManager.createGame(gameName)
    performer.sendMessage("Success")
  }
}