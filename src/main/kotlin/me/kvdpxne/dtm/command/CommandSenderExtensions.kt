package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.player.localUser
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.asPerformer(): Performer {
  if (this !is Player) {
    return BukkitConsolePerformer.INSTANCE
  }

  return this.localUser.performer
}