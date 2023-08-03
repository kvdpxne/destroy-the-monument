package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.shared.TeleportationHistoryStorage
import me.kvdpxne.dtm.user.UserPerformer

object TeleportBackCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (TeleportationHistoryStorage.history.empty()) {
      performer.sendMessage("The previous location is unknown.")
      return
    }

    val last = TeleportationHistoryStorage.history.pop()
    val player = performer.getPlayer() ?: return
    player.teleport(last)
  }
}