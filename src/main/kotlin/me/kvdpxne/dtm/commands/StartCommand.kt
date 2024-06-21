package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserPerformer

object StartCommand {

  private fun startGame(game: Game?, user: User) {
    if (null == game) {
      user.sendMessage("No found game.")
      user.sendMessage("Usage: /dtm start <GAME_NAME>")
      return
    }

    game.start()

    user.sendMessage("The game ${game.name} has started.")
  }

  fun createStartCommand(): Command = CommandBuilder()
    .name("start")
    .parent("dtm")
    .handler<UserPerformer> { performer, parameter ->
      if (parameter.isEmpty()) {
        performer.user.run {
          startGame(GameManager.findByUser(this), this)
        }
        return@handler
      }

      performer.user.run {
        startGame(GameManager.findByName(parameter.asText()), this)
      }
    }
    .build()
}