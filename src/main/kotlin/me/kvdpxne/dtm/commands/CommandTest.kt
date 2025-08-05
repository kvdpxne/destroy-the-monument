package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.scoreboard.ScoreboardManager
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

fun createTestCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("test")
    .handler { performer: Performer, _: Array<Any> ->
      if (performer !is LocalUserPerformer) {
        performer.sendMessage("Sorry but the command is only available from the local user level.")
        return@handler
      }

      ScoreboardManager.playerJoinGame(performer.player!!, performer.game!!, performer.team!!)

      performer.sendMessage("The command completed its operation successfully.")
    }
    .build()
}