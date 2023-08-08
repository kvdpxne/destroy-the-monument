package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.data.TeamDao
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.user.UserPerformer

object CreateTeamCommand : Executor<UserPerformer> {

  // Usage: /dtm CreateTeam <TEAM_NAME>
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    val name = parameter.asText()
    TeamDao.insert(DefaultTeamColor.findByIdentityKey(name)!!)
    performer.sendMessage(name)
  }
}