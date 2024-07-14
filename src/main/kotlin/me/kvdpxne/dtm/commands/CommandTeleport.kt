package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.shared.TeleportationHistoryStorage
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import me.kvdpxne.dtm.user.UserPerformer

fun createTeleportCommand(): Command {
  // Usage: /dtm teleport <WORLD_NAME>
  return CommandBuilder()
    .name("teleport")
    .aliases("tp")
    .handler<UserPerformer> { performer, parameter ->
      val player = performer.getPlayer() ?: return@handler

      if (parameter.isEmpty()) {
        performer.sendMessage("Usage: /dtm teleport <WORLD_NAME>")
        return@handler
      }

      // The name of the world registered as an arena.
      val name = parameter.asText()

      WorldLoaderHelper.getWorld(name).let {
        if (null == it) {
          performer.sendMessage("World named \"$name\" does not exist.")
          return@handler
        }

        TeleportationHistoryStorage.push(player.uniqueId, player.location)
        player.teleport(it.spawnLocation)
        try {
          player.sendMessage(it.name)
          player.sendMessage(it.uid.toString())
        } catch (e: Exception) {
          // ignore
        }

      }
    }
    .build()
}