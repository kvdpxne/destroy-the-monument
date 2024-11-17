package me.kvdpxne.dtm.listeners.block

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.world.WorldsHolder
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBurnEvent

/**
 * Listener to handle the [BlockBurnEvent], preventing certain blocks from
 * burning under specific conditions within designated arenas or the
 * lobby world.
 *
 * @since 0.1.0
 */
object BlockBurnListener : Listener {

  /**
   * Handles the [BlockBurnEvent], cancelling the event if it occurs in the
   * lobby world or near specified revival positions within an arena.
   *
   * @param event The [BlockBurnEvent] triggered when a block starts burning.
   *
   * @since 0.1.0
   */
  @EventHandler
  fun handleBlockBurn(
    event: BlockBurnEvent
  ) {
    if (event.isCancelled) {
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