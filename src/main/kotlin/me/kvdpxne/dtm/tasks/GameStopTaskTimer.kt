package me.kvdpxne.dtm.tasks

import me.kvdpxne.dtm.game.Game
import org.bukkit.scheduler.BukkitRunnable

class GameStopTaskTimer(
  private val game: Game
) : BukkitRunnable() {

  override fun run() {
    this.game.stop()
  }
}