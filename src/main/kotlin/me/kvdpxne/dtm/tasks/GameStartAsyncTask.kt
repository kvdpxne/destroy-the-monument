package me.kvdpxne.dtm.tasks

import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.basics.task.AbstractAsynchronousTask
import me.kvdpxne.dtm.shared.minecraft.bukkit.runSynchronousTask

class GameStartAsyncTask(
  private val game: LocalGame,
  private var remainingSeconds: Int = 30
) : AbstractAsynchronousTask() {

  override fun execute() {
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
      --this.remainingSeconds
      return
    }

    if (0 == this.remainingSeconds % 10) {
      this.game.sendMessage("&6&lDTM &7> &7Pozostało &6${this.remainingSeconds} &7sekund do startu gry.")
      --this.remainingSeconds
      return
    }

    --this.remainingSeconds
  }
}