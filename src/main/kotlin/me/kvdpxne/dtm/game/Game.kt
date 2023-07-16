package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.profession.Kit
import org.bukkit.entity.Player

class Game {

  private val teams = mapOf<TeamColor, Team>(
    DefaultTeamColor.BLUE to Team(DefaultTeamColor.BLUE),
    DefaultTeamColor.RED to Team(DefaultTeamColor.RED)
  )

  fun joinTeam(player: Player, kit: Kit, teamColor: TeamColor) {
    teams[teamColor]!!.teammates += Teammate(player, kit)
  }
}