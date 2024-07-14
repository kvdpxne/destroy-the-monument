package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandManager
import me.kvdpxne.dtm.command.Performer

fun createHelpCommand(): Command {
  // Usage: /dtm help
  return CommandBuilder()
    .name("help")
    .handler<Performer> { performer, _ ->
      performer.sendMessages(
        "Available commands:",
        *CommandManager.commands.map { "/${it.fullName()}" }.toTypedArray()
      )
    }
    .build()
}