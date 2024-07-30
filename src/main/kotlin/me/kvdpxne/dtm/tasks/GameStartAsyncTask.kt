package me.kvdpxne.dtm.tasks

import me.kvdpxne.dtm.game.GameStates
import me.kvdpxne.dtm.game.temporary.Game
import me.kvdpxne.dtm.game.temporary.MIN_HOSTAGE_SIZE_
import me.kvdpxne.dtm.shared.basics.task.AbstractAsynchronousTask
import me.kvdpxne.dtm.shared.minecraft.bukkit.runSynchronousTask

class GameStartAsyncTask(
  private val game: Game,
  private var remainingSeconds: Int = 30
) : AbstractAsynchronousTask() {

  override fun execute() {
    if (MIN_HOSTAGE_SIZE_ > this.game.numberOfHostagesEnrolled) {
      this.game.state = GameStates.INITIALIZED
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