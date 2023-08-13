package me.kvdpxne.dtm.command.restricted

import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.user.UserPerformer

object AddTeamCommand : Executor<UserPerformer> {

  // Usage: /dtm AddTeam <GAME_NAME> <TEAM_IDENTITY>
  override fun execute(performer: UserPerformer, parameter: Parameter) {
    if (2 > parameter.length()) {
      performer.sendMessage("Usage: /dtm AddTeam <GAME_NAME> <TEAM_IDENTITY>")
      return
    }

    val gameName = parameter.asText()
    val game = GameManager.findByName(gameName)

    if (null == game) {
      performer.sendMessage("An game named $gameName does not exist.")
      return
    }

    val teamName = parameter.asText(1)
    val team = DefaultTeamColor.findByIdentityKey(teamName)

    if (null == team) {
      performer.sendMessage("An team named $teamName does not exist.")
      return
    }

    game.addTeam(Team(team, game))
    performer.sendMessage("Success")
  }
}