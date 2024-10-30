package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.shared.world.toLocation
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * @since 0.1.0
 */
fun createTeleportBackCommand(): Command<LocalUserPerformer> {
  // Usage: /dtm teleportBack
  return CommandBuilder.begin<LocalUserPerformer>("teleportBack")
    .aliases("tpback", "tpb")
    .handler { performer, _ ->
      val player = performer.player ?: return@handler
      val position = performer.user.cache.teleportationHistory.removeLastOrNull()

      if (null == position) {
        player.sendMessage("Previous position is unknown.")
        return@handler
      }

      player.teleport(position.toLocation())
      player.sendMessage("You have been moved to an earlier position.")
    }
    .build()
}