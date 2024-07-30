package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.ArenaService

fun createArenaListCommand(): Command {
  // Usage: /dtm arena list
  return CommandBuilder()
    .name("list")
    .handler<Performer> { performer, _ ->
      performer.sendMessages(
        "&6&lDTM &7> &7Lista dostępnych aren:",
        *ArenaService.findArenas()
          .map {
            "&6&lDTM &7> &a${it.name}"
          }
          .toTypedArray()
      )
    }
    .build()
}