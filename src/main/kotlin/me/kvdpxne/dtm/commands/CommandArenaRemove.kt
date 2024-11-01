package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService

/**
 * @since 0.1.0
 */
fun createArenaRemoveCommand(): Command<Performer> {
  // Usage: /dtm arena remove <ARENA_NAME> [GAME_NAME]
  return CommandBuilder.begin<Performer>("remove")
    .aliases("rem", "rv", "delete", "del")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .parameter(
      Parameters.gameNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      //
      val arenaName: String = parameters[0] as String

      //
      val arena: Arena = ArenaService.findArenaByName(arenaName)
        ?: throw CommandException(
          Configuration.NO_FOUND_ARENA
            .replace("{ARENA_NAME}", arenaName)
        )

      ArenaService.deleteArenaByIdentifier(arena.identifier)

      performer.sendMessage("")
    }
    .build()
}