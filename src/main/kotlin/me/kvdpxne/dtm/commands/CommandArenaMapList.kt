package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.ArenaService

fun createArenaMapListCommand(): Command {
  // Usage: /dtm arena map list
  return CommandBuilder()
    .name("list")
    .handler<Performer> { performer, _ ->
      performer.sendMessages(
        "&6&lDTM &7> &7Lista dostępnych map aren:",
        *ArenaService.findArenas()
          .map {
            "&6&lDTM &7> &a${it.name} ${it.map?.name ?: "&cBrak"}"
          }
          .toTypedArray()
      )
    }
    .build()
}