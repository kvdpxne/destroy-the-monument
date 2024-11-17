package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer

/**
 * @since 0.1.0
 */
fun createCoinsTopCommand(): Command<Performer> {
  // Usage: /dtm coins top
  return CommandBuilder.begin<Performer>("top")
    .handler { performer, _ ->
      // TODO command body
    }
    .build()
}