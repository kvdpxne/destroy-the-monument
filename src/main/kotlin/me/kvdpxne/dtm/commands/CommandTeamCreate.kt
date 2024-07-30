package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.data.DaoTeam
import me.kvdpxne.dtm.game.TeamService
import me.kvdpxne.dtm.user.UserPerformer

fun createTeamCreateCommand(): Command {
  // Usage: /dtm team create <TEAM_NAME>
  return CommandBuilder()
    .name("create")
    .handler<UserPerformer> { performer, parameter ->
      if (parameter.isEmpty()) {
        performer.sendMessage("Usage: /dtm CreateTeam <TEAM_NAME>")
        return@handler
      }
      val name = parameter.asText()
      DaoTeam.insertTeam(TeamService.findTeamIdentityByName(name)!!)
      performer.sendMessage(name)
    }
    .build()
}