package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.shared.resetExpBar
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Ability(
  val delay: Int,
  val whenReady: WhenAbilityReadyHandler,
) {

  var ready: Boolean = true
    private set

  var taskIdentifier = -1
    private set

  fun markRead() {
    this.ready = true
  }

  fun run(player: Player) {
    this.ready = false
    player.resetExpBar()

    this.taskIdentifier = AbilityCooldownTaskTimer(
      this,
      this.delay,
      player
    ).runTaskTimerAsynchronously(
      JavaPlugin.getPlugin(DestroyTheMonument::class.java),
      5L,
      20L
    ).taskId
  }
}