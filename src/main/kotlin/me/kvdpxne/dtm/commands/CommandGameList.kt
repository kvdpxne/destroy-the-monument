package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createGameListCommand(): Command {
  return CommandBuilder()
    .name("list")
    .handler<Performer> { performer, arguments ->
      performer.sendMessages(
        ""
      )
    }
    .build()
}