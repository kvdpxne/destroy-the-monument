package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.builderWorldNameParameter
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import me.kvdpxne.dtm.shared.minecraft.bukkit.toEntityPosition
import me.kvdpxne.dtm.user.UserPerformer

fun createTeleportCommand(): Command {
  // Usage: /dtm teleport <WORLD_NAME>
  return CommandBuilder()
    .name("teleport")
    .aliases("tp")
    .parameter(
      builderWorldNameParameter()
        .required()
        .build()
    )
    .handler<UserPerformer> { performer, parameter ->
      val player = performer.player ?: return@handler

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

        performer.user.cache.teleportationHistory.addLast(player.location.toEntityPosition())
        player.teleport(it.spawnLocation)
      }
    }
    .build()
}