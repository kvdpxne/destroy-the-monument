package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPistonExtendEvent

/**
 * @since 0.1.0
 */
object BlockPistonExtendListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.LOWEST
  )
  fun handleBlockPiston(
    event: BlockPistonExtendEvent
  ) {
    if (event.isCancelled) {
      return
    }

    //
    val world = event.block.location

    //
    for (game: Game in GameManager.games) {
      if (!game.isRunning && !game.isStopping) {
        continue
      }

      val arenaWorld: World = game.currentArena?.map?.world
        ?: continue

      if (world != arenaWorld) {
        continue
      }

      event.cancel()
    }
  }
}