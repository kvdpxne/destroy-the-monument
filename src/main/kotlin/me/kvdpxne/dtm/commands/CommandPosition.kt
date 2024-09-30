package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.user.LocalUserPerformer
import org.bukkit.Location

/**
 * @since 0.1.0
 */
fun createPositionCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("position")
    .aliases("pos", "location", "loc")
    .handler { performer, _ ->
      val location: Location = performer.player?.location
        ?: return@handler

      performer.sendMessages(
        "Your current position is:",
        "World: ${location.world.name}",
        "X: ${location.x}",
        "Y: ${location.y}",
        "Z: ${location.z}",
        "Pitch: ${location.pitch}",
        "Yaw: ${location.yaw}"
      )
    }
    .build()
}