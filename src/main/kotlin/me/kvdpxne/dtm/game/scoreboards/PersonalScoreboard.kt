package me.kvdpxne.dtm.game.scoreboards

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

class PersonalScoreboard(private val player: Player) {

  companion object {
    private const val MAX_LINES = 15
  }

  private val scoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
  private val objective: Objective = scoreboard.registerNewObjective("board", "dummy").apply {
    displaySlot = DisplaySlot.SIDEBAR
  }
  private val lines = Array<Team?>(MAX_LINES) { null }
  private var type: GameScoreboardState.ScoreboardType =
    GameScoreboardState.ScoreboardType.LOBBY

  init {
    setupTeams()
    player.scoreboard = scoreboard
  }

  fun destroy() {
    player.scoreboard = Bukkit.getScoreboardManager().mainScoreboard
    lines.forEachIndexed { index, team ->
      team?.unregister()
      scoreboard.resetScores(getEntry(index))
    }
    objective.unregister()
  }

  private fun setupTeams() {
    for (i in 0 until MAX_LINES) {
      val team = scoreboard.registerNewTeam("line_$i")
      val entry = getEntry(i)
      team.addEntry(entry)
      lines[i] = team
    }
  }

  private fun getEntry(index: Int): String =
    ChatColor.entries[index].toString() + ChatColor.RESET

  fun setType(newType: GameScoreboardState.ScoreboardType) {
    if (type != newType) {
      type = newType
    }
  }

  fun update(template: List<(Player) -> String>) {
    objective.displayName = ChatColor.translateAlternateColorCodes('&', "&6&lMINI GRA")

    val lineCount = minOf(template.size, MAX_LINES)

    for (i in 0 until lineCount) {
      val text = template[i](player)
      lines[i]?.prefix = ChatColor.translateAlternateColorCodes('&', text)
      objective.getScore(getEntry(i)).score = lineCount - i
    }

    for (i in lineCount until MAX_LINES) {
      scoreboard.resetScores(getEntry(i))
    }
  }
}