package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createVersionCommand(): Command {
  return CommandBuilder()
    .name("version")
    .aliases("ver", "v")
    .handler<Performer> { performer, arguments ->
      performer.sendMessages(
        "v0.1.0"
      )
    }
    .build()
}