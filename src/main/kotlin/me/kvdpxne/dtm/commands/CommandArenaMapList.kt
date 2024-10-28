package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.arena.ArenaService

/**
 * @since 0.1.0
 */
fun createArenaMapListCommand(): Command<Performer> {
  // Usage: /dtm arena map list
  return CommandBuilder.begin<Performer>("list")
    .handler { performer, _ ->
      performer.sendMessages(
        "&6&lDTM &7> &7Lista dostępnych map aren:",
        *ArenaService.findArenas()
          .map {
            "&6&lDTM &7> &a${it.name} ${it.map?.world?.name ?: "&cBrak"}"
          }
          .toTypedArray()
      )
    }
    .build()
}