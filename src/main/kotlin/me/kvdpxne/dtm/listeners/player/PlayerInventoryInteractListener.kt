package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.gui.GuiHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryType

object PlayerInventoryInteractListener : Listener {

  @EventHandler
  fun handlePlayerInventoryInteract(
    event: InventoryInteractEvent
  ) {
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