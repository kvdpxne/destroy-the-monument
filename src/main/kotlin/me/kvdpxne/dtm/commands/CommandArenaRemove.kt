package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createArenaRemoveCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("remove")
    .aliases("rem", "rv", "delete", "del")
    .handler { performer, parameters ->

    }
    .build()
}