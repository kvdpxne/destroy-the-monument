package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createArenaRemoveCommand(): Command {
  return CommandBuilder()
    .name("remove")
    .handler<Performer> { performer, arguments ->

    }
    .build()
}