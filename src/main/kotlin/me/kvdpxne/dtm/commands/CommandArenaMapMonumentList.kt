package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createArenaMapMonumentListCommand(): Command {
  // Usage: /dtm arena map monument list <ARENA_NAME>
  return CommandBuilder()
    .name("list")
    .handler<Performer> { performer, arguments ->

    }
    .build()
}