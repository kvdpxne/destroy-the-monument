package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.registeredCommandMap

fun createBaseCommand(): Command = CommandBuilder()
  .name("dtm")
  .handler<Performer> { performer, parameter ->
    if (parameter.isEmpty()) {
      performer.sendMessages(
        "The command needs at least one argument to work properly.",
        "Use \"/dtm help\" to display all commands."
      )
      return@handler
    }

    val name = parameter.asText()
    val childCommand = registeredCommandMap[
      registeredCommandMap.keys.find {
        it.matches("dtm")
      }
    ]?.find {
      it.matches(name)
    }

    if (null == childCommand) {
      performer.sendMessage("The command named \"$name\" was not found.")
      return@handler
    }

    childCommand.handler(performer, parameter.asParameter())
  }
  .build()