package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.user.UserPerformer
import org.bukkit.Bukkit

object TeleportCommand : Executor<UserPerformer> {

  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val player = performer.getPlayer() ?: return

    if (parameter.isEmpty()) {
      performer.sendMessage("The command requires at least 1 argument.")
      return
    }

    // The name of the world registered as an arena.
    val name = parameter.asText()

    Bukkit.getWorld(name).let {
      if (null == it) {
        performer.sendMessage("World named \"$name\" does not exist.")
        return
      }

      val spawnLocation = it.spawnLocation
      TeleportBackCommand.previousLocation = spawnLocation

      player.teleport(spawnLocation)
    }
  }
}