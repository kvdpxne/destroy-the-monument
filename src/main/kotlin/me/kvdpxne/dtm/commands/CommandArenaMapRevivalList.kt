package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService

fun createArenaMapRevivalListCommand(): Command {
  // Usage: /dtm arena map revival list <ARENA_NAME>
  return CommandBuilder()
    .name("list")
    .parameter(
      ParameterBuilder<String>()
        .name("ARENA_NAME")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .build()
    )
    .handler<Performer> { performer, arguments ->
      if (arguments.isEmpty()) {
        return@handler
      }

      val arena: Arena? = ArenaService.findArenaByName(arguments.asText())
      if (null == arena) {
        return@handler
      }

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