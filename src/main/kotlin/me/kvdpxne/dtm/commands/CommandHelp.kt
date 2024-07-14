package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.registeredCommandMap

fun createHelpCommand(): Command {
  // Usage: /dtm help
  return CommandBuilder()
    .name("help")
    .handler<Performer> { performer, _ ->
      performer.sendMessage("Available commands:")
      registeredCommandMap.forEach { (parent, child) ->
        child.forEach { command ->
          performer.sendMessage("/${parent.name} ${command.name}")
        }
      }
    }
    .build()
}