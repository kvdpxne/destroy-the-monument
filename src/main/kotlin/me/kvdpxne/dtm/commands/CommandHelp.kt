package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandManager
import me.kvdpxne.dtm.command.Performer

fun createHelpMap(children: Array<Command>): Array<String>? {
  if (children.isEmpty()) {
    return null
  }

  val names = mutableListOf<String>()
  for (command in children) {
    if (command.children.isEmpty() || !command.executable) {
      names.add("/${command.fullName()}")
    }
    val array = createHelpMap(command.children) ?: continue
    names.addAll(array)
  }

  return names.toTypedArray()
}

fun createHelpCommand(): Command {
  // Usage: /dtm help
  return CommandBuilder()
    .name("help")
    .handler<Performer> { performer, _ ->

      val commands = mutableListOf<String>()
      for (command in CommandManager.commands) {
        if (command.children.isEmpty() || !command.executable) {
          commands.add("/${command.fullName()}")
        }

        val array = createHelpMap(command.children) ?: continue
        commands.addAll(array)
      }

      performer.sendMessages(
        "Available commands:",
        *commands.toTypedArray()
      )
    }
    .build()
}