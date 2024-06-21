package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.data.TeamDao
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.user.UserPerformer

fun createCreateTeamCommand(): Command = CommandBuilder()
  .name("createTeam")
  .parent("dtm")
  .handler<UserPerformer> { performer, parameter ->
    if (parameter.isEmpty()) {
      performer.sendMessage("Usage: /dtm CreateTeam <TEAM_NAME>")
      return@handler
    }
    val name = parameter.asText()
    TeamDao.insert(DefaultTeamColor.findByIdentityKey(name)!!)
    performer.sendMessage(name)
  }
  .build()