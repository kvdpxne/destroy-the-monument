package me.kvdpxne.dtm.tasks

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameState
import me.kvdpxne.dtm.game.MIN_HOSTAGE_SIZE_
import me.kvdpxne.dtm.shared.bukkit.runSynchronousTask
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class GameStartTaskTimer(
  private val game: Game,
  private var remainingSeconds: Int = 30
) : BukkitRunnable() {

  override fun run() {
    if (MIN_HOSTAGE_SIZE_ > game.playersInGame()) {
      game.sendMessage("&6&lDTM &7> &fThe start of the game arena was &cinterrupted &fdue to insufficient players.")
      game.state = GameState.INITIALIZED
      this.cancel()
      return
    }

    if (game.state.isStarted()) {
      this.cancel()
      return
    }

    if (0 >= remainingSeconds) {
      runSynchronousTask {
        game.start()
      }
      game.sendMessage("&6&lDTM &7> &fThe game has been &asuccessfully &7started.")
      this.cancel()
      return
    }

    if (5 >= remainingSeconds) {
      game.sendMessage("&6&lDTM &7> &fThe game will start in &6$remainingSeconds &fseconds.")
      --remainingSeconds
      return
    }

    if (0 == remainingSeconds % 10) {
      game.sendMessage("&6&lDTM &7> &6$remainingSeconds &fseconds left to start the game.")
      --remainingSeconds
      return
    }

    --remainingSeconds
  }
}