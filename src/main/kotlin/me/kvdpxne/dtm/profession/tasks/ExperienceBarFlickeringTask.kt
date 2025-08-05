package me.kvdpxne.dtm.profession.tasks

import me.kvdpxne.dtm.shared.player.fillExperienceBar
import me.kvdpxne.dtm.shared.player.isSurvivalOrAdventure
import me.kvdpxne.dtm.shared.player.resetExperienceBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class ExperienceBarFlickeringTask(
  private val target: Player
) : BukkitRunnable() {

  override fun run() {
    if (!this.target.gameMode.isSurvivalOrAdventure) {
      return
    }

    if (0.0F != this.target.exp) {
      this.target.resetExperienceBar()
      return
    }
    this.target.fillExperienceBar()
  }
}