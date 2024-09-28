package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService

/**
 * @since 0.1.0
 */
fun createArenaMapRevivalListCommand(): Command<Performer> {
  // Usage: /dtm arena map revival list <ARENA_NAME>
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
        ?: throw CommandException("")

      //
      for (it in arena.revivalPositions) {
        performer.sendMessages(
          "Team: ${it.team.name}",
          "x: ${it.x}, y: ${it.y}, z: ${it.z}",
          "pitch: ${it.pitch}, yaw: ${it.yaw}"
        )
      }
    }
    .build()
}