package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaService
import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey

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
          GeneralConfiguration.NO_FOUND_ARENA
            .replace("{ARENA_NAME}", arenaName)
        )

      // Unikatowa nazwa obiektu gry przechowywanej w bazie danych.
      val gameName: String = parameters[1] as String

      // Obiekt gry znaleziony na podstawie unikatowej nazwy gry.
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException(
          GeneralConfiguration.NO_FOUND_GAME
            .replace("{GAME_NAME}", gameName)
        )

      ArenaService.deleteArenaByIdentifier(arena.identifier)

      performer.prepareMessage(EnumMessageKey.COMMAND_ARENA_REMOVE)
        .format(
          Formatter.begin(2)
            .with("ARENA_NAME", arena.name)
            .with("GAME_NAME", game.name)
        )
        .useChat()
        .send()
    }
    .build()
}