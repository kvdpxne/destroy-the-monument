package me.kvdpxne.dtm.command.overt

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

object LeaveCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val game = GameManager.findGameByUser(performer.user)
    if (null == game) {
      performer.sendMessage("You are not in any game.")
      return
    }
    game.removeHostage(performer.user)
    performer.sendMessage("You left the game.")
  }
}