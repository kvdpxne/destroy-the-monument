package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.shared.player.fillExperienceBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * @param ability
 * @param remainingSeconds
 * @param target
 *
 * @since 0.1.0
 */
class AbilityCooldownTaskTimer(
  // @formatter:off
  private val ability         : Ability,
  private var remainingSeconds: Int,
  private val target          : Player
  // @formatter:on
) : BukkitRunnable() {

  /**
   * @since 0.1.0
   */
  private val part = 1.0F / this.remainingSeconds

  /**
   * @since 0.1.0
   */
  override fun run() {
    this.target.level = this.remainingSeconds
    this.target.exp += this.part

    if (0 >= this.remainingSeconds) {
      this.cancel()

      this.ability.let {
        it.markReady()

        if (!it.isActivatable) {
          it.whenReady(this.target)
        }
      }

      this.target.fillExperienceBar()
      return
    }

    this.remainingSeconds--
  }
}