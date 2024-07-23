package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.command.builderArenaNameParameter
import me.kvdpxne.dtm.command.builderGameNameParameter

fun createArenaAddCommand(): Command {
  // Usage: /dtm arena add <ARENA_NAME> <GAME_NAME>
  return CommandBuilder()
    .name("add")
    .parameter(
      builderArenaNameParameter()
        .required()
        .build()
    )
    .parameter(
      builderGameNameParameter()
        .required()
        .build()
    )
    .handler<Performer> { performer, arguments ->
      val arena = arguments.asFoundArena()

      if (null == arena) {
        performer.sendMessage("An arena named ${arguments.asText()} does not exist.")
        return@handler
      }

      val game = arguments.asFoundGame(1)

      if (null == game) {
        performer.sendMessage("An game named ${arguments.asText(1)}does not exist.")
        return@handler
      }

      game.addArena(arena)
      performer.sendMessage("Success")
    }
    .build()
}