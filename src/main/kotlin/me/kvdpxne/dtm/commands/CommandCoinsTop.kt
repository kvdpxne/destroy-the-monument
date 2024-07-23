package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createCoinsTopCommand(): Command {
  // Usage: /dtm coins top
  return CommandBuilder()
    .name("top")
    .handler<Performer> { performer, arguments ->

    }
    .build()
}