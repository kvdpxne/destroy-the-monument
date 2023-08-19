package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

object StopCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (parameter.isEmpty()) {
      performer.sendMessage("Usage: /dtm stop <GAME_NAME>")
      return
    }

    val gameName = parameter.asText()
    val game = GameManager.findByName(gameName)

    if (null == game) {
      performer.sendMessage("No found game.")
      return
    }

    try {
      game.stop(performer.user)
    } catch (exception: IllegalArgumentException) {
      // It's stupid but for the current phase the point is that the world in
      // which the player is located will be unloaded. it cannot be discharged
      // while any player is on this world.
      performer.sendMessage("The game cannot be stopped.")
      return
    }
  }
}