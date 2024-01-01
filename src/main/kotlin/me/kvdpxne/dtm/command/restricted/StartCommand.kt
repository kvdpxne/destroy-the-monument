package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserPerformer

object StartCommand : Executor<UserPerformer> {

  private fun startGame(game: Game?, user: User) {
    if (null == game) {
      user.sendMessage("No found game.")
      user.sendMessage("Usage: /dtm start <GAME_NAME>")
      return
    }

    game.start()

    user.sendMessage("The game ${game.name} has started.")
  }

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (parameter.isEmpty()) {
      performer.user.run {
        startGame(GameManager.findByUser(this), this)
      }
      return
    }

    performer.user.run {
      startGame(GameManager.findByName(parameter.asText()), this)
    }
  }
}