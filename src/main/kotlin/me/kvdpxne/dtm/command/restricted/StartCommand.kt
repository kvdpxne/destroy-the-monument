package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserPerformer

object StartCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return

    if (parameter.isEmpty()) {
      performer.sendMessage("The command requires at least 1 argument.")
      return
    }

    val name = parameter.asText()
    val game = GameManager.findGameByArenaName(name)

    if (null == game) {
      performer.sendMessage("")
      return
    }
  }
}