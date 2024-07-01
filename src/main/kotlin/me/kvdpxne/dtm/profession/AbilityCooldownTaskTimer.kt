package me.kvdpxne.dtm.profession

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class AbilityCooldownTaskTimer(
  private val ability         : Ability,
  private var remainingSeconds: Int,
  private val target          : Player
) : BukkitRunnable() {

  override fun run() {
    synchronized(this.target) {
      this.target.level = this.remainingSeconds
    }

    if (0 >= this.remainingSeconds) {
      this.cancel()
      this.ability.markRead()
      return
    }

    this.remainingSeconds--
  }
}