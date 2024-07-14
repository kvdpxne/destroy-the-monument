package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.ArenaManager

fun createArenaListCommand(): Command {
  return CommandBuilder()
    .name("list")
    .handler<Performer> { performer, _ ->
      performer.sendMessages(
        "List of currently available arenas:",
        *ArenaManager.registeredArenas.map { it.name }.toTypedArray()
      )
    }
    .build()
}