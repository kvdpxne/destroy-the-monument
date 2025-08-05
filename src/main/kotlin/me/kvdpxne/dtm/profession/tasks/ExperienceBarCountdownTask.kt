package me.kvdpxne.dtm.profession.tasks

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.profession.Ability
import me.kvdpxne.dtm.shared.player.fillExperienceBar
import me.kvdpxne.dtm.shared.player.isSurvivalOrAdventure
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * @param ability
 * @param remainingSeconds
 * @param target
 *
 * @since 0.1.0
 */
class ExperienceBarCountdownTask(
  // @formatter:off
  private val ability         : Ability,
  private var remainingSeconds: Int,
  private val target          : Player
  // @formatter:on
) : BukkitRunnable() {

  /**
   * @since 0.1.0
   */
  private val part: Float = 1.0F / (this.remainingSeconds + 1)

  /**
   * @since 0.1.0
   */
  override fun run() {
    if (this.target.gameMode.isSurvivalOrAdventure) {
      this.target.level = this.remainingSeconds
      this.target.exp += this.part
    }

    if (0 >= this.remainingSeconds) {
      this.cancel()

      this.ability.let {
        it.markReady()

        if (!it.isActivatable) {
          it.whenReady(this.target)
        }

        it.countdownTaskId = ExperienceBarFlickeringTask(this.target)
          .runTaskTimerAsynchronously(
            DestroyTheMonument.instance,
            20L,
            15L
          )
          .taskId
      }

      this.target.fillExperienceBar()
      return
    }

    this.remainingSeconds--
  }
}