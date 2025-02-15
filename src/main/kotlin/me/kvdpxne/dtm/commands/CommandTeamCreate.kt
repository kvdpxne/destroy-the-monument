package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Parameters
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.team.TeamColors
import me.kvdpxne.dtm.team.TeamImpl
import me.kvdpxne.dtm.team.TeamService
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * @since 0.1.0
 */
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

      TeamService.createTeam(team)

      performer.prepareMessage(EnumTranslationKey.COMMAND_TEAM_CREATE)
        .format(
          Formatter.begin(1)
            .with("TEAM_NAME", team.name)
        )
        .useChat()
        .send()
    }
    .build()
}