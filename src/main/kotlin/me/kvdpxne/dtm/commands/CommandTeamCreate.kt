package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.data.DaoTeam
import me.kvdpxne.dtm.game.TeamImpl
import me.kvdpxne.dtm.game.TeamColors

fun createTeamCreateCommand(): Command<Performer> {
  // Usage: /dtm team create <TEAM_NAME>
  return CommandBuilder.begin<Performer>("create")
    .parameter(
      Parameters.teamNameParameter()
        .required()
        .build()
    )
    .handler { performer, parameters ->
      //
      val teamName: String = parameters[0] as String

      //
      val color = TeamColors.findTeamColorByName(teamName)

      if (null == color) {
        performer.sendMessage("Team name does not exist: $teamName")
        return@handler
      }

      val team = TeamImpl(teamName, color)
      DaoTeam.insertTeam(team)

      performer.sendMessage(teamName)
    }
    .build()
}