package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createGameListCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("list")
    .handler { performer, _ ->
      performer.sendMessages(
        ""
      )
    }
    .build()
}