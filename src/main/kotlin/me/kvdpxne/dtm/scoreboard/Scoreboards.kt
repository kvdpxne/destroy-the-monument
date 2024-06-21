package me.kvdpxne.dtm.scoreboard

import me.kvdpxne.dtm.PluginContext
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
  team.prefix = PluginContext.textFormatter.format("$color&l${name.uppercase()}&f")

  return team
}