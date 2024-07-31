package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.data.DaoTeam
import me.kvdpxne.dtm.game.TeamIdentity
import me.kvdpxne.dtm.game.TeamColors
import me.kvdpxne.dtm.user.UserPerformer

fun createTeamCreateCommand(): Command {
  // Usage: /dtm team create <TEAM_NAME>
  return CommandBuilder()
    .name("create")
    .parameter(
      ParameterBuilder<String>()
        .name("TEAM_NAME")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .build()
    )
    .handler<UserPerformer> { performer, parameter ->
      if (parameter.isEmpty()) {
        performer.sendMessage("Usage: /dtm team create <TEAM_NAME>")
        return@handler
      }

      val name = parameter.asText()
      val color = TeamColors.findTeamColorByName(name)

      if (null == color) {
        performer.sendMessage("Team name does not exist: $name")
        return@handler
      }

      val team = TeamIdentity(name, color)
      DaoTeam.insertTeam(team)

      performer.sendMessage(name)
    }
    .build()
}