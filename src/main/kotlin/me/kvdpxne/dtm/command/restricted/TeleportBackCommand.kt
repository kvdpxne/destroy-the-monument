package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.user.UserPerformer
import org.bukkit.Location

object TeleportBackCommand : Executor<UserPerformer> {

  var previousLocation: Location? = null

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (null == previousLocation) {
      performer.sendMessage("The previous location is unknown.")
      return
    }

    val player = performer.getPlayer() ?: return
    player.teleport(previousLocation)
  }
}