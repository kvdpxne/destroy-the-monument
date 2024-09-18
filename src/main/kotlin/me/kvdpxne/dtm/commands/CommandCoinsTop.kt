package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

fun createCoinsTopCommand(): Command<Performer> {
  // Usage: /dtm coins top
  return CommandBuilder.begin<Performer>("top")
    .handler { performer, _ ->

    }
    .build()
}