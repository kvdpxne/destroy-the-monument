package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.user.LocalUserPerformer
import org.bukkit.Location

fun createPositionCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("position")
    .aliases("pos", "location", "loc")
    .handler { performer, _ ->
      val location: Location = performer.player?.location ?: return@handler
      performer.sendMessages(
        "world: ${location.world.name}",
        "x: ${location.x}",
        "y: ${location.y}",
        "z: ${location.z}",
        "pitch: ${location.pitch}",
        "yaw: ${location.yaw}"
      )
    }
    .build()
}