package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.shared.TeleportationHistoryStorage
import me.kvdpxne.dtm.user.UserPerformer

fun createTeleportBackCommand(): Command = CommandBuilder()
  .name("teleportback")
  .aliases("tpback", "tpb")
  .parent("dtm")
  .handler<UserPerformer> { performer, _ ->
    val player = performer.getPlayer() ?: return@handler
    val position = TeleportationHistoryStorage.pop(player.uniqueId)

    if (null == position) {
      player.sendMessage("Previous position is unknown.")
      return@handler
    }

    player.teleport(position)
    player.sendMessage("You have been moved to an earlier position.")
  }
  .build()