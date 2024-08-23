package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.ParameterBuilder
import me.kvdpxne.dtm.command.ParameterValidators
import me.kvdpxne.dtm.data.DaoGameTeam
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.user.UserPerformer

fun createTeamAddCommand(): Command {
  // Usage: /dtm team add <GAME_NAME> <TEAM_IDENTITY>
  return CommandBuilder()
    .name("add")
    .parameter(
      ParameterBuilder<String>()
        .name("GAME_NAME")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .build()
    )
    .parameter(
      ParameterBuilder<String>()
        .name("TEAM_NAME")
        .validationBy(ParameterValidators.STRING_VALIDATOR)
        .required()
        .build()
    )
    .handler<UserPerformer> { performer, parameter ->
      val gameName = parameter.asText()
      val game = performer.user.game

      if (null == game) {
        performer.sendMessage("An game named $gameName does not exist.")
        return@handler
      }

      val teamName = parameter.asText(1)
      val team = TeamService.findTeamByName(teamName)

      if (null == team) {
        performer.sendMessage("An team named $teamName does not exist.")
        return@handler
      }

      DaoGameTeam.insertGameTeam(game.identifier, team.identifier)

      performer.sendMessage("Success")
    }
    .build()
}