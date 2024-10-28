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
fun createArenaMapMonumentListCommand(): Command<Performer> {
  // Usage: /dtm arena map monument list <ARENA_NAME>
  return CommandBuilder.begin<Performer>("list")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
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

      arena.monumentPositions
        .sortedBy {
          it.team.name
        }
        .forEach {
          performer.sendMessages(
            "Team: ${it.team.name}",
            "x: ${it.x}, y: ${it.y}, z: ${it.z}",
          )
        }
    }
    .build()
}