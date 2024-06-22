package me.kvdpxne.dtm.scoreboard

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.shared.BukkitScoreboard
import me.kvdpxne.dtm.shared.BukkitTeam
import org.bukkit.Bukkit
import org.bukkit.ChatColor

fun createServerScoreboard(): BukkitScoreboard {
  return Bukkit.getScoreboardManager().newScoreboard
}

fun createServerTeam(scoreboard: BukkitScoreboard, name: String, color: ChatColor): BukkitTeam {
  val team = scoreboard.registerNewTeam(name)

  team.setAllowFriendlyFire(false)
  team.setCanSeeFriendlyInvisibles(true)
  team.prefix = "$color&l${name.uppercase()} &r".colorize()

  return team
}