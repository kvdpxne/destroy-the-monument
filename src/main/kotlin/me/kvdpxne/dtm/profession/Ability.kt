package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.shared.resetExperienceBar
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Ability(
  // @formatter:off
  val delay          : Int,
  val whenReady      : WhenAbilityReadyHandler = {},
  val readyAfterDeath: Boolean                 = false,
  val readyAfterKill : Boolean                 = false
  // @formatter:on
): Cloneable {

  var ready: Boolean = true
    private set

  var taskIdentifier = -1
    private set

  fun markReady() {
    this.ready = true
  }

  fun run(player: Player, a: Boolean = false) {
    this.ready = false
    player.resetExperienceBar()

    val remainingSeconds = if (a) {
      Math.round(this.delay * 0.334F) + this.delay
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

  fun cancelCooldown() {
    if (0 > this.taskIdentifier) {
      return
    }

    Bukkit.getScheduler().cancelTask(this.taskIdentifier)
  }

  public override fun clone(): Ability {
    return Ability(
      this.delay,
      this.whenReady,
      this.readyAfterDeath,
      this.readyAfterKill
    )
  }
}