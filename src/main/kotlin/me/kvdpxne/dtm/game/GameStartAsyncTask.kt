package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.shared.task.runSynchronousTask
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import org.bukkit.scheduler.BukkitRunnable

/**
 * @param game
 * @param remainingSeconds
 *
 * @param 0.1.0
 */
internal class GameStartAsyncTask internal constructor(
  // @formatter:off
  private val game            : LocalGame,
  private val force           : Boolean   = false,
  private var remainingSeconds: Int       = 30
  // @formatter:on
) : BukkitRunnable() {

  private fun decrese() {
    for (team: LocalTeam in this.game.teams) {
      for (teammate: Teammate in team.teammates) {
        teammate.user.performer.player?.level = this.remainingSeconds
      }
    }

    --this.remainingSeconds
  }

  override fun run() {
    if (Configuration.MIN_TEAMMATES_SIZE > this.game.numberOfHostagesEnrolled) {
      this.game.setAsInitialized()
      this.cancel()

      this.game.sendMessages(
        "&6&lDTM &7> &7Odliczanie do wystartowania gry zostało wstrzymane.",
        "&6&lDTM &7> &7Powód: &cNiewystarczająca liczba graczy."
      )
      return
    }

    //
    if (this.game.isRunning || this.game.isStopping) {
      this.cancel()
      return
    }

    if (0 >= this.remainingSeconds) {
      this.cancel()

      runSynchronousTask {
        this.game.start()
      }
      return
    }

    if (5 >= this.remainingSeconds) {
      this.game.sendMessage("&6&lDTM &7> &7Gra wystartuje za &6${this.remainingSeconds} &7sekund.")
      this.decrese()
      return
    }

    if (0 == this.remainingSeconds % 10) {
      this.game.sendMessage("&6&lDTM &7> &7Pozostało &6${this.remainingSeconds} &7sekund do startu gry.")
      this.decrese()
      return
    }

    this.decrese()
  }
}