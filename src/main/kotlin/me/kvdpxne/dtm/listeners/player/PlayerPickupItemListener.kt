package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.world.WorldsHolder
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent

/**
 * @since 0.1.0
 */
object PlayerPickupItemListener : Listener {

  /**
   * @since 0.1.0
   */
  private fun clearItemsWhenPickup(
    event: PlayerPickupItemEvent,
  ) {
    event.cancel()
    event.item.itemStack.type = Material.AIR
  }

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerPickupItem(
    event: PlayerPickupItemEvent
  ) {
    if (event.isCancelled) {
      return
    }

    val world: World = event.player.world
    val lobbyWorld: World? = WorldsHolder.lobbyWorld

    if (world == lobbyWorld) {
      clearItemsWhenPickup(event)
      return
    }

    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)
    if (world == arena?.map?.world) {
      return
    }

    clearItemsWhenPickup(event)
  }
}