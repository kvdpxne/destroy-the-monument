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
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.MessageKeys

/**
 * @since 0.1.0
 */
fun createTeamAddCommand(): Command<Performer> {
  // Usage: /dtm team add <GAME_NAME> <TEAM_IDENTITY>
  return CommandBuilder.begin<Performer>("add")
    .parameter(
      Parameters.gameNameParameter()
        .required()
        .build()
    )
    .parameter(
      Parameters.teamNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      // Unikalna nazwa gry.
      val gameName: String = parameters[0] as String

      //
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException(
          Configuration.NO_FOUND_GAME
            .replace("{GAME_NAME}", gameName)
        )

      //
      val teamName: String = parameters[1] as String

      //
      val team: Team = TeamService.findTeamByName(teamName)
        ?: throw CommandException(
          Configuration.NO_FOUND_TEAM
            .replace("{TEAM_NAME}", teamName)
        )

      //
      GameService.insertGameTeam(game, team)

      TranslationService.chains()
        .receiver(performer)
        .message(MessageKeys.COMMAND_TEAM_ADD)
        .formatter(
          Formatter.begin(2)
            .with("TEAM_NAME", team.name)
            .with("GAME_NAME", game.name)
        )
        .send()
    }
    .build()
}