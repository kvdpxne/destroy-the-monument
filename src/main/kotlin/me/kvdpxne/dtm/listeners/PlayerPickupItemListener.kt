package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.shared.event.cancel
import org.bukkit.Material
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
  @EventHandler
  fun handlePlayerPickupItem(
    event: PlayerPickupItemEvent
  ) {
    if (event.isCancelled) {
      return
    }

    event.cancel()
    event.item.itemStack.type = Material.AIR
  }
}