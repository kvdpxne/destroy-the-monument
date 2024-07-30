package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.temporary.Team
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.user.UserPerformer

fun createTeamAddCommand(): Command {
  // Usage: /dtm team add <GAME_NAME> <TEAM_IDENTITY>
  return CommandBuilder()
    .name("add")
    .handler<UserPerformer> { performer, parameter ->
      val gameName = parameter.asText()
      val game = GameManager.findGameByName(gameName)

      if (null == game) {
        performer.sendMessage("An game named $gameName does not exist.")
        return@handler
      }

      val teamName = parameter.asText(1)
      val team = TeamService.findTeamIdentityByName(teamName)

      if (null == team) {
        performer.sendMessage("An team named $teamName does not exist.")
        return@handler
      }

      game.addTeam(Team(team, game))
      performer.sendMessage("Success")
    }
    .build()
}