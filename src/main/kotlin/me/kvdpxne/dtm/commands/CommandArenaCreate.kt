package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.user.UserPerformer

fun createArenaCreateCommand(): Command {
  // Usage: /dtm arena create <ARENA_NAME>
  return CommandBuilder()
    .name("create")
    .parameter(
      ParameterBuilder<String>()
        .name("arena_name")
        .required()
        .build()
    )
    .handler<UserPerformer> { performer, arguments ->
      val arenaName = arguments.asText()
      val arena = Arena(arenaName)

      ArenaService.insertArena(arena)

      performer.sendMessages(
        "&6&lDTM &7> &7Utworzono nową arenę o nazwie: &a$arenaName",
        "&6&lDTM &7> &7Pamiętaj, że arena nie jest jeszcze gotowa aby przypisać ją do gry."
      )
    }
    .build()
}