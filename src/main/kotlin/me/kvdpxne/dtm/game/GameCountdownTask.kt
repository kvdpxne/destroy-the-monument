package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.player.resetExperienceBar
import me.kvdpxne.dtm.shared.player.resetExperienceBarLevel
import me.kvdpxne.dtm.shared.task.AsynchronousTask
import me.kvdpxne.dtm.shared.task.runSynchronousTask
import me.kvdpxne.dtm.shared.text.toSingleLines
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import org.bukkit.entity.Player

/**
 * @param game
 * @param remainingSeconds
 *
 * @param 0.1.0
 */
internal class GameCountdownTask internal constructor(
  // @formatter:off
  internal val game            : LocalGame,
  internal var remainingSeconds: Int = GeneralConfiguration.FSFS
  // @formatter:on
) : AsynchronousTask() {

  /**
   * @since 0.1.0
   */
  private fun enrolledPlayers(
    playerFunction: (Player?) -> Unit
  ) {
    for (team: LocalTeam in this.game.teams) {
      for (teammate: Teammate in team.teammates) {
        playerFunction(teammate.user.performer.player)
      }
    }
  }

  /**
   * @since 0.1.0
   */
  private fun decreaseRemainingSeconds() {
    this.enrolledPlayers { player: Player? ->
      player?.level = this.remainingSeconds
    }

    --this.remainingSeconds
  }

  /**
   * @since 0.1.0
   */
  private fun resetExperience() {
    this.enrolledPlayers { player: Player? ->
      player?.resetExperienceBarLevel()
      player?.resetExperienceBar()
    }
  }

  /**
   * @since 0.1.0
   */
  private fun sendNotification(
    key: EnumMessageKey
  ) {
    this.game.prepareMessage(key)
      .format(
        Formatter.begin(1)
          .with("REMAINING_TIME", this.remainingSeconds)
      )
      .useChat()
      .send()

    this.decreaseRemainingSeconds()
  }

  /**
   * @since 0.1.0
   */
  private fun sendInformation(
    key: EnumMessageKey
  ) {
    this.game.prepareMessage(key)
      .withoutFormat()
      .useChat()
      .send()
  }

  /**
   * @since 0.1.0
   */
  override fun execute() {
    if (GeneralConfiguration.MIN_TEAMMATES_SIZE > this.game.numberOfHostagesEnrolled) {
      this.cancel()
      this.game.setAsInitialized()

      this.sendInformation(EnumMessageKey.GAME_STARTING_COUNTDOWN_CANCELLED)
      this.resetExperience()

      return
    }

    // If the game does not have a STARTING state, it is likely that the task
    // of counting down to the start of the game was started by a bug in the
    // code and should be stopped immediately.
    if (!this.game.isStarting) {
      this.cancel()

      Debug.log {
        """
          BUG: The countdown task to the start of the game was started in a
          game that does not have a STARTING state.
        """.toSingleLines()
      }
      return
    }

    if (0 >= this.remainingSeconds) {
      this.cancel()

      runSynchronousTask {
        this.game.start()
      }

      this.sendInformation(EnumMessageKey.GAME_STARTING_COUNTDOWN_FINISH)
      return
    }

    if (5 >= this.remainingSeconds) {
      this.sendNotification(EnumMessageKey.GAME_STARTING_COUNTDOWN_FASTER)
      return
    }

    if (0 == this.remainingSeconds % 10) {
      this.sendNotification(EnumMessageKey.GAME_STARTING_COUNTDOWN_STANDARD)
      return
    }

    this.decreaseRemainingSeconds()
  }
}