package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.shared.world.toEntityPosition
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
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
      // The world found
      val world: World = attemptObtainWorld(performer, parameters)

      val player = performer.player ?: return@handler

      performer.user.cache.teleportationHistory.addLast(player.location.toEntityPosition())
      player.teleport(world.spawnLocation)

      performer.prepareMessage(EnumTranslationKey.COMMAND_TELEPORT_TO)
        .format(
          Formatter.begin(1)
            .with("WORLD_NAME", world.name)
        )
        .useChat()
        .send()
    }
    .build()
}