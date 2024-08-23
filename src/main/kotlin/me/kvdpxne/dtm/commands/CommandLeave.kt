package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
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
      game.removeHostage(performer.user)
      performer.sendMessage("You left the game.")
    }
    .build()
}
