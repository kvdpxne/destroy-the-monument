package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserPerformer

object StopCommand {

  private fun stopGame(game: Game?, user: User) {
    if (null == game) {
      user.sendMessage("No found game.")
      user.sendMessage("Usage: /dtm stop <GAME_NAME>")
      return
    }

    try {
      game.stop()
    } catch (exception: IllegalArgumentException) {
      // It's stupid but for the current phase the point is that the world in
      // which the player is located will be unloaded. it cannot be discharged
      // while any player is on this world.
      user.sendMessage("The game cannot be stopped.")
      return
    }

    user.sendMessage("The ${game.name} game has been stopped.")
  }

  fun createStopCommand(): Command = CommandBuilder()
    .name("stop")
    .parent("dtm")
    .handler<UserPerformer> { performer, parameter ->
      if (parameter.isEmpty()) {
        performer.user.run {
          stopGame(GameManager.findByUser(this), this)
        }
        return@handler
      }

      performer.user.run {
        stopGame(GameManager.findByName(parameter.asText()), this)
      }
    }
    .build()
}