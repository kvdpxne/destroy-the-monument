package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder

fun createGameCommand(): Command {
  return CommandBuilder()
    .name("game")
    .hub()
    .children(
      createGameCreateCommand()
    )
    .build()
}