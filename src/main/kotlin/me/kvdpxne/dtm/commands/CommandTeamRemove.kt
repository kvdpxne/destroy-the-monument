package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.TeamService

/**
 * @since 0.1.0
 */
fun createTeamRemoveCommand(): Command<Performer> {
  // Usage: /dtm team remove <TEAM_NAME> [GAME_NAME]
  return CommandBuilder.begin<Performer>("remove")
    .aliases("rem", "rv", "delete", "del")
    .parameter(
      Parameters.gameNameParameter()
        .optional()
        .build()
    )
    .handler { performer, parameters ->
      //
      val teamName: String = parameters[0] as String

      //
      val team: Team = TeamService.findTeamByName(teamName)
        ?: throw CommandException(
          Configuration.NO_FOUND_TEAM
            .replace("{TEAM_NAME}", teamName)
        )

      // Unikatowa nazwa obiektu gry przechowywanej w bazie danych.
      val gameName: String = parameters[1] as String

      // Obiekt gry znaleziony na podstawie unikatowej nazwy gry.
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException(
          Configuration.NO_FOUND_GAME
            .replace("{GAME_NAME}", gameName)
        )

      //
      GameService.deleteGameTeam(game, team)

      performer.sendMessage("&6&lDTM &7> &fDrużyna została usunięta.")
    }
    .build()
}