package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.ArenaService
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
  @EventHandler(priority = EventPriority.LOW)
  fun handleBlockPiston(
    event: BlockPistonExtendEvent
  ) {
    if (event.isCancelled) {
      return
    }

    //
    event.block.world.let { world: World ->
      ArenaService.findLoadedArenaByWorldIdentifier(world.uid) ?: return
    }

    //
    event.cancel()
  }
}