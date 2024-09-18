package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createTeamListCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("list")
    .handler { performer, parameters ->

    }
    .build()
}