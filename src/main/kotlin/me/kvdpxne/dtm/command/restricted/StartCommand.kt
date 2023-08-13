package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

object StartCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (parameter.isEmpty()) {
      performer.sendMessage("Usage: /dtm start <GAME_NAME>")
      return
    }

    val gameName = parameter.asText()
    val game = GameManager.findByName(gameName)

    if (null == game) {
      performer.sendMessage("No found game.")
      return
    }

    game.start(performer.user)
  }
}