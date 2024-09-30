package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.game.Team

/**
 * @since 0.1.0
 */
fun createTeamListCommand(): Command<Performer> {
  // Usage: /dtm team list <GAME_NAME>
  return CommandBuilder.begin<Performer>("list")
    .parameter(
      Parameters.gameNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      // Unikatowa nazwa obiektu gry przechowywanej w bazie danych.
      val gameName: String = parameters[0] as String

      // Obiekt gry znaleziony na podstawie unikatowej nazwy gry.
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException(
          Configuration.NO_FOUND_GAME
            .replace("{GAME_NAME}", gameName)
        )

      performer.sendMessages(
        "&6&lDTM &7> &7Drużyny przypisane do gry o nazwie &6${game.name}&7:",
        *game.teams
          .sortedBy { it.name }
          .map {
            "&8> ${it.displayName}"
          }
          .toTypedArray()
      )
    }
    .build()
}