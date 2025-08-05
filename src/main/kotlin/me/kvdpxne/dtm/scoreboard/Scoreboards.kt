package me.kvdpxne.dtm.scoreboard

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.text.colorize
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

object Scoreboards {

  val FORMATTED_TITLE: String by lazy {
    "&7[&6&lDTM&7]".colorize
  }

  fun fsf(
    player: Player,
    time: Int,
    fsf: Collection<Arena>,
    signed: Int,
    online: Int,
    coins: Long
  ) {
    FastBoard(player).let {
      it.updateTitle("&7[&6&lDTM&7]".colorize)
      it.updateLines(
        listOf(
          "&7Start gry: &e&l00:$time",
          "",
          "&7Max: &6&lbez limitu",
          "&7Min: &6&l2",
          "",
          "&7Zapisani: &6&l${signed}",
          "&7Online: &6&l${online}",
          "",
          "&7Głosowanie na mapę:"
        ).colorize
      )

      for ((index: Int, arena: Arena) in fsf.withIndex()) {
        it.updateLine(9 + index, "&7${index + 1}. &e${arena.name}".colorize)
      }

      it.updateLine(13, "&7Monety:".colorize)
      it.updateLine(14, "&6&l$coins".colorize)
    }
  }
}

fun createServerScoreboard(): Scoreboard {
  return Bukkit.getScoreboardManager().newScoreboard
}

fun createServerTeam(scoreboard: Scoreboard, name: String, color: ChatColor): Team {
  val team = scoreboard.registerNewTeam(name)

  team.setAllowFriendlyFire(false)
  team.setCanSeeFriendlyInvisibles(true)
  team.prefix = "$color"

  return team
}

fun initScoreboard(
  player: Player,
  redPlayerCount: Int,
  redMonumentCount: Int,
  bluePlayerCount: Int,
  blueMonumentCount: Int,
  coins: Long
): FastBoard {
  return FastBoard(player).apply {
    this.updateTitle("&c&lD&f&lT&9&lM".colorize)
    this.updateLines(
      listOf(
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
      ).colorize
    )
  }
}

fun updateScoreboardTime(fastBoard: FastBoard, time: String) {
  fastBoard.updateLine(0, "&7Czas gry: &e&l$time".colorize)
}

fun updateRedPlayerCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(3, "&7- Gracze: &6&l$count".colorize)
}

fun updateFirstMonumentCounter(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(4, "&7- Monumenty: &6&l$count".colorize)
}

fun updateBluePlayerCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(7, "&7- Gracze: &6&l$count".colorize)
}

fun updateSecondMonumentCounter(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(8, "&7- Monumenty: &6&l$count".colorize)
}

fun updateKillCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(10, "&7Zabojstwa: &6&l$count".colorize)
}

fun updateDeathCount(fastBoard: FastBoard, count: Int) {
  fastBoard.updateLine(11, "&7Smierci: &6&l$count".colorize)
}

fun updateCoinCount(fastBoard: FastBoard, count: Long) {
  fastBoard.updateLine(14, "&6&l$count".colorize)
}