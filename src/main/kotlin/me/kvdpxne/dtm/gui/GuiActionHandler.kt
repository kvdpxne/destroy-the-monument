package me.kvdpxne.dtm.gui

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryType

object GuiActionHandler : Listener {

  @EventHandler
  fun handleInventoryClick(event: InventoryClickEvent) {
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

  @EventHandler
  fun handleInventoryInteract(event: InventoryInteractEvent) {
    val inventory = event.inventory
    if (InventoryType.CHEST != inventory.type) {
      return
    }
    if (inventory.holder !is GuiHolder) {
      return
    }
    event.isCancelled = true
  }
}