package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUserPerformer
import org.bukkit.Location

/**
 * @since 0.1.0
 */
fun createPositionCommand(): Command<LocalUserPerformer> {
  return CommandBuilder.begin<LocalUserPerformer>("position")
    .aliases("pos", "location", "loc")
    .handler { performer, _ ->
      //
      val location: Location = performer.player?.location
        ?: return@handler

      performer.prepareMessage(EnumMessageKey.COMMAND_POSITION)
        .format(
          Formatter.begin(6)
            .with("WORLD_NAME", location.world.name)
            .with("X", location.x)
            .with("Y", location.y)
            .with("Z", location.z)
            .with("PITCH", location.pitch)
            .with("YAW", location.yaw)
        )
        .useChat()
        .send()
    }
    .build()
}