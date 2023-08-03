package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.shared.TeleportationHistoryStorage
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import me.kvdpxne.dtm.user.UserPerformer

object TeleportCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return

    if (parameter.isEmpty()) {
      performer.sendMessage("The command requires at least 1 argument.")
      return
    }

    // The name of the world registered as an arena.
    val name = parameter.asText()

    WorldLoaderHelper.getWorld(name).let {
      if (null == it) {
        performer.sendMessage("World named \"$name\" does not exist.")
        return
      }

      TeleportationHistoryStorage.history.push(player.location)
      player.teleport(it.spawnLocation)
    }
  }
}