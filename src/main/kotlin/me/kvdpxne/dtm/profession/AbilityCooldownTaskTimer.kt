package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.shared.fillExpBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class AbilityCooldownTaskTimer(
  private val ability         : Ability,
  private var remainingSeconds: Int,
  private val target          : Player
) : BukkitRunnable() {

  private val fs = 1.0F / this.remainingSeconds

  override fun run() {
    this.target.level = this.remainingSeconds
    this.target.exp += this.fs

    if (0 >= this.remainingSeconds) {
      this.cancel()

      this.target.fillExpBar()
      this.ability.markRead()
      this.ability.whenReady(this.target)

      return
    }

    this.remainingSeconds--
  }
}