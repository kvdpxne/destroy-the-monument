package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.gui.GuiHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

/**
 * @since 0.1.0
 */
object PlayerInventoryClickListener : Listener {

  @EventHandler
  fun handlePlayerInventoryClick(
    event: InventoryClickEvent
  ) {
    val inventory = event.inventory
    if (InventoryType.CHEST != inventory.type) {
      return
    }
    val holder = inventory.holder
    if (holder !is GuiHolder) {
      return
    }
    event.isCancelled = true
    holder.handleAction(event)
  }
}