package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.ArenaImpl

fun createArenaCreateCommand(): Command<Performer> {
  // Usage: /dtm arena create <ARENA_NAME>
  return CommandBuilder.begin<Performer>("create")
    .parameter(
      Parameters.arenaNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      // Nazwa areny.
      val arenaName: String = parameters[0] as String

      // Nowo utworzony obiekt areny.
      val arena: Arena = ArenaImpl(arenaName)

      //
      ArenaService.insertArena(arena)

      performer.sendMessages(
        "&6&lDTM &7> &7Utworzono nową arenę o nazwie: &a$arenaName",
        "&6&lDTM &7> &7Pamiętaj, że arena nie jest jeszcze gotowa aby przypisać ją do gry."
      )
    }
    .build()
}