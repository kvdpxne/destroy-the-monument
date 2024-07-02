package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.shared.resetExpBar
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Ability(
  // @formatter:off
  val delay          : Int,
  val whenReady      : WhenAbilityReadyHandler = {},
  val readyAfterDeath: Boolean                 = false,
  val readyAfterKill : Boolean                 = false
  // @formatter:on
) {

  var ready: Boolean = true
    private set

  var taskIdentifier = -1
    private set

  fun markReady() {
    this.ready = true
  }

  fun run(player: Player, a: Boolean = false) {
    this.ready = false
    player.resetExpBar()

    val remainingSeconds = if (a) {
      Math.round(this.delay * 0.333F) + this.delay
    } else {
      this.delay
    }

    this.taskIdentifier = AbilityCooldownTaskTimer(
      this,
      remainingSeconds,
      player
    ).runTaskTimerAsynchronously(
      DestroyTheMonument.instance,
      2L,
      20L
    ).taskId
  }
}