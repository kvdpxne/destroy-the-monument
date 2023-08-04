package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.shared.TeleportationHistoryStorage
import me.kvdpxne.dtm.user.UserPerformer

object TeleportBackCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return
    val position = TeleportationHistoryStorage.pop(player.uniqueId)

    if (null == position) {
      player.sendMessage("Previous position is unknown.")
      return
    }

    player.teleport(position)
    player.sendMessage("You have been moved to an earlier position.")
  }
}