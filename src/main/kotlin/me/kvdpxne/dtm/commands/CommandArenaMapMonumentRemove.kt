package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createArenaMapMonumentRemoveCommand(): Command {
  return CommandBuilder()
    .name("remove")
    .aliases("rm", "delete", "del")
    .handler<Performer> { performer, arguments ->

    }
    .build()
}