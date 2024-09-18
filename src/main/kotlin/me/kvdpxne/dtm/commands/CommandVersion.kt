package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createVersionCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("version")
    .aliases("ver", "v")
    .handler { performer, _ ->
      //
      //
      performer.sendMessages(
        "v0.1.0"
      )
    }
    .build()
}