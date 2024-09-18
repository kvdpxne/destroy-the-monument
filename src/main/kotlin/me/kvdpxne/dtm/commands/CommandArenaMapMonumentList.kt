package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createArenaMapMonumentListCommand(): Command<Performer> {
  // Usage: /dtm arena map monument list <ARENA_NAME>
  return CommandBuilder.begin<Performer>("list")
    .handler { performer, _ ->

    }
    .build()
}