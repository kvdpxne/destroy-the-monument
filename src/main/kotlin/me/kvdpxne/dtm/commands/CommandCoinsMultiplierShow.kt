package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderUserNameParameter

fun createCoinsMultiplierShowCommand(): Command {
  // Usage: /dtm coins multiplier show [USER_NAME]
  return CommandBuilder()
    .name("show")
    .parameter(
      builderUserNameParameter()
        .optional()
        .build()
    )
    .handler<Performer> { performer, arguments ->

    }
    .build()
}