package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.shared.WorldLoaderHelper
import me.kvdpxne.dtm.shared.minecraft.bukkit.toEntityPosition
import me.kvdpxne.dtm.user.LocalUserPerformer
import org.bukkit.World

/**
 * @since 0.1.0
 */
fun createTeleportCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm teleport <WORLD_NAME>
  return CommandBuilder.begin<LocalUserPerformer>("teleport")
    .aliases("tp")
    .parameter(
      Parameters.localWorldNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val worldName: String = parameters[0] as String

      //
      val world: World = WorldLoaderHelper.getWorld(worldName)
        ?: throw CommandException("World named \"$worldName\" does not exist.")

      val player = performer.player ?: return@handler

      performer.user.cache.teleportationHistory.addLast(player.location.toEntityPosition())
      player.teleport(world.spawnLocation)
    }
    .build()
}