package me.kvdpxne.dtm.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder


typealias InventoryHandler = (InventoryClickEvent) -> Unit

class GuiHolder : InventoryHolder {

  private val actions = mutableMapOf<Int, InventoryHandler>()
  internal var inventory: Inventory? = null

  fun handleActionHandler(slot: Int, handler: InventoryHandler?) {
    actions[slot] = handler ?: { _ -> }
  }

  fun handleAction(event: InventoryClickEvent) {
    //
    val key = event.rawSlot
    //
    //
    actions.getOrDefault(key) {
      it.isCancelled = true
    }(event)
  }

  override fun getInventory(): Inventory {
    return inventory!!
  }
}