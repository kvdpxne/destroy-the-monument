package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.shared.debug.Debug

fun createDebugCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("debug")
    .handler { performer, _ ->
      Debug.togglePrintInConsole()
      performer.sendMessage("Debug ${Debug.printInConsole}")
    }
    .build()
}