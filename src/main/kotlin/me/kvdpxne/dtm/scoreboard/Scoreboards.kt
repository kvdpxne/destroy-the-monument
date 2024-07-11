package me.kvdpxne.dtm.scoreboard

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.colorizeAll
import me.kvdpxne.dtm.shared.BukkitScoreboard
import me.kvdpxne.dtm.shared.BukkitTeam
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

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

fun initScoreboard(
  player: Player,
  redPlayerCount: Int,
  redMonumentCount: Int,
  bluePlayerCount: Int,
  blueMonumentCount: Int,
  coins: Int
): FastBoard {
  return FastBoard(player).apply {
    this.updateTitle("&c&lD&f&lT&9&lM".colorize())
    this.updateLines(
      arrayOf(
        "&7Czas gry: &e&l00:00",
        "",
        "&c&lCzerwoni:",
        "&7- Gracze: &6&l$redPlayerCount",
        "&7- Monumenty: &6&l$redMonumentCount",
        "",
        "&b&lNiebiescy:",
        "&7- Gracze: &6&l$bluePlayerCount",
        "&7- Monumenty: &6&l$blueMonumentCount",
        "",
        "&7Zabojstwa: &6&l0",
        "&7Smierci: &6&l0",
        "",
        "&7Monety:",
        "&6&l$coins"
      ).colorizeAll()
    )
  }
}

fun updateScoreboardTime(fastBoard: FastBoard, time: String) {
  fastBoard.updateLine(0, "&7Czas gry: &e&l$time".colorize())
}

fun updateRedPlayerCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(3, "&7- Gracze: &6&l$count".colorize())
}

fun updateRedMonumentCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(4, "&7- Monumenty: &6&l$count".colorize())
}

fun updateBluePlayerCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(7, "&7- Gracze: &6&l$count".colorize())
}

fun updateBlueMonumentCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(8, "&7- Monumenty: &6&l$count".colorize())
}

fun updateKillCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(10, "&7Zabojstwa: &6&l$count".colorize())
}

fun updateDeathCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(11, "&7Smierci: &6&l$count".colorize())
}

fun updateCoinCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(14, "&6&l$count".colorize())
}