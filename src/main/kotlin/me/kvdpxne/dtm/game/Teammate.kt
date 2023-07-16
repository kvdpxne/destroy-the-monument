package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.profession.Kit
import org.bukkit.entity.Player

class Teammate(
  val player: Player,
  var kit: Kit,
  var teamColor: DefaultTeamColor = DefaultTeamColor.NONE
)