package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.CommandException
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.data.DaoGameTeam
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameService
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.TeamService

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
      //
      val gameName: String = parameters[0] as String

      //
      val game: Game<Team> = GameService.findGameByName(gameName)
        ?: throw CommandException("An game named $gameName does not exist.")

      //
      val teamName: String = parameters[1] as String

      //
      val team = TeamService.findTeamByName(teamName)
        ?: throw CommandException("Team $teamName does not exist.")

      //
      DaoGameTeam.insertGameTeam(game.identifier, team.identifier)

      //
      performer.sendMessage("Success")
    }
    .build()
}