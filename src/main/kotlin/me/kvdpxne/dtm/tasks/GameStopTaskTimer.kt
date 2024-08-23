package me.kvdpxne.dtm.tasks

import me.kvdpxne.dtm.game.LocalGame
import org.bukkit.scheduler.BukkitRunnable

class GameStopTaskTimer(
  private val game: LocalGame
) : BukkitRunnable() {

  override fun run() {
    this.game.stop()
  }
}