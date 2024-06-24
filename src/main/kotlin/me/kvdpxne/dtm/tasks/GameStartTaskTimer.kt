package me.kvdpxne.dtm.tasks

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameState
import me.kvdpxne.dtm.game.MIN_HOSTAGE_SIZE_
import org.bukkit.scheduler.BukkitRunnable

class GameStartTaskTimer(
  private val game: Game,
  remainingSeconds: Int = 30
) : BukkitRunnable() {

  private var i = remainingSeconds

  override fun run() {
    if (MIN_HOSTAGE_SIZE_ > game.playersInGame()) {
      game.sendMessage("&6&lDTM &7> &fThe start of the game arena was &cinterrupted &fdue to insufficient players.")
      game.state = GameState.INITIALIZED
      this.cancel()
      return
    }

    if (0 >= i) {
      game.start()
      game.sendMessage("&6&lDTM &7> &fThe game has been &asuccessfully &7started.")
      this.cancel()
      return
    }

    if (5 >= i) {
      game.sendMessage("&6&lDTM &7> &fThe game will start in &6$i &fseconds.")
      --i
      return
    }

    if (0 == i % 10) {
      game.sendMessage("&6&lDTM &7> &6$i &fseconds left to start the game.")
      --i
      return
    }

    --i
  }
}