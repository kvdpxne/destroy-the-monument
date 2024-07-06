package me.kvdpxne.dtm.shared

import org.bukkit.event.inventory.InventoryInteractEvent

fun InventoryInteractEvent.closeInventory() {
  this.whoClicked.closeInventory()
}