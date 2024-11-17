package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.shared.task.cancelTask
import me.kvdpxne.dtm.shared.player.fillExperienceBar
import me.kvdpxne.dtm.shared.player.resetExperienceBar
import me.kvdpxne.dtm.shared.player.resetExperienceBarLevel
import org.bukkit.entity.Player

class Ability(
  // @formatter:off
  val delay          : Int,
  val whenReady      : WhenAbilityReadyHandler = {},
  val isActivatable  : Boolean                 = true,
  val readyAfterDeath: Boolean                 = false,
  val readyAfterKill : Boolean                 = false
  // @formatter:on
): Cloneable {

  /**
   * @since 0.1.0
   */
  var isReady: Boolean = true
    private set

  /**
   * @since 0.1.0
   */
  var isActive: Boolean = true
    private set

  /**
   * @since 0.1.0
   */
  var taskIdentifier = -1
    private set

  /**
   * @since 0.1.0
   */
  fun markReady() {
    this.isReady = true
  }

  /**
   * @since 0.1.0
   */
  fun markActive() {
    this.isActive = true
  }

  /**
   * @since 0.1.0
   */
  fun cancelCooldown() {
    if (0 > this.taskIdentifier) {
      return
    }

    //
    cancelTask(this.taskIdentifier)
  }

  /**
   * @since 0.1.0
   */
  fun renewDelayed(
    player: Player?,
    a: Boolean = false
  ) {
    if (null == player) {
      return
    }

    //
    this.isReady = false
    this.isActive = false

    //
    player.resetExperienceBarLevel()
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

  /**
   * @since 0.1.0
   */
  fun renew(player: Player?) {
    if (null == player) {
      return
    }

    this.cancelCooldown()

    this.markReady()

    if (!this.isActivatable) {
      this.whenReady(player)
    }

    player.resetExperienceBarLevel()
    player.fillExperienceBar()
  }

  /**
   * @since 0.1.0
   */
  public override fun clone(): Ability {
    return Ability(
      this.delay,
      this.whenReady,
      this.isActivatable,
      this.readyAfterDeath,
      this.readyAfterKill
    )
  }
}