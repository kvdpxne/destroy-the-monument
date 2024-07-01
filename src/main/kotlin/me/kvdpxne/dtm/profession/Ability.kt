package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.DestroyTheMonument
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Ability(
  val delay: Int
) {

  var ready: Boolean = true
    private set

  fun markRead() {
    this.ready = true
  }

  fun run(player: Player) {
    this.ready = false
    AbilityCooldownTaskTimer(
      this,
      this.delay,
      player
    ).runTaskTimerAsynchronously(
      JavaPlugin.getPlugin(DestroyTheMonument::class.java),
      5L,
      20L
    )
  }
}