package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipA
import me.kvdpxne.dtm.shared.minecraft.bukkit.moveToLobby
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.user.UserPerformer

fun createLeaveCommand(): Command {
  return CommandBuilder()
    .name("leave")
    .handler<UserPerformer> { performer, _ ->
      val game = performer.user.game

      if (null == game) {
        performer.sendMessage("You are not in any game.")
        return@handler
      }

      if (game.isInArena(performer.user)) {
        game.findTeammateByHostage(performer.user)?.leave()
        performer.sendMessage("You left the game.")
        return@handler
      }

      game.removeHostage(performer.user)
      performer.player?.reset()
      performer.player?.moveToLobby()
      performer.player?.equipA()

      performer.sendMessage("You left the game.")
    }
    .build()
}
