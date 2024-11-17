package me.kvdpxne.dtm.listeners.block

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.world.WorldsHolder
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockSpreadEvent

/**
 * Listener to handle [BlockSpreadEvent], specifically preventing the spread
 * of fire near revival points in arenas or within the lobby world.
 *
 * @since 0.1.0
 */
object BlockSpreadListener : Listener {

  /**
   * Handles the [BlockSpreadEvent], canceling the event if the block spread
   * involves fire and occurs near specified revival points in an arena or
   * within the lobby world.
   *
   * @param event The [BlockSpreadEvent] triggered when a block spreads
   *              (e.g., fire).
   *
   * @since 0.1.0
   */
  @EventHandler
  fun handleBlockSpread(
    event: BlockSpreadEvent
  ) {
    if (event.isCancelled) {
      return
    }

    if (Material.FIRE != event.newState.type) {
      return
    }

    val world: World = event.block.world
    val lobbyWorld: World? = WorldsHolder.lobbyWorld

    if (world == lobbyWorld) {
      event.cancel()
      return
    }

    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)
    if (world != arena?.map?.world) {
      return
    }

    val location: Location = event.block.location
    for (revivalPosition: RevivalPosition<*> in arena.revivalPositions) {
      if (revivalPosition.isNear(
          location.x,
          location.y,
          location.z,
          GeneralConfiguration.RADIUS_OF_BLOCK_INTERACTION
        )
      ) {
        event.cancel()
      }
    }
  }
}