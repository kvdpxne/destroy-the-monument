package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createTeamRemoveCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("remove")
    .handler { performer, parameters ->

    }
    .build()
}