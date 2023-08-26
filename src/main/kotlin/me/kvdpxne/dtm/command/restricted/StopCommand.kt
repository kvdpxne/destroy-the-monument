package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserPerformer

object StopCommand : Executor<UserPerformer> {

  private fun stopGame(game: Game?, user: User) {
    if (null == game) {
      user.sendMessage("No found game.")
      user.sendMessage("Usage: /dtm stop <GAME_NAME>")
      return
    }

    try {
      game.stop(user)
    } catch (exception: IllegalArgumentException) {
      // It's stupid but for the current phase the point is that the world in
      // which the player is located will be unloaded. it cannot be discharged
      // while any player is on this world.
      user.sendMessage("The game cannot be stopped.")
      return
    }

    user.sendMessage("The ${game.name} game has been stopped.")
  }

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (parameter.isEmpty()) {
      performer.user.run {
        stopGame(GameManager.findByUser(this), this)
      }
      return
    }

    performer.user.run {
      stopGame(GameManager.findByName(parameter.asText()), this)
    }
  }
}