package me.kvdpxne.dtm.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

open class Gui(displayName: String, rows: Rows) {

  private val holder = GuiHolder()
  private val inventory = Bukkit.createInventory(holder, rows.size, displayName)

  init {
    holder.inventory = this.inventory
  }

  private fun hasItem(slot: Int): Boolean {
    val item = inventory.getItem(slot)
    return null != item && Material.AIR != item.type
  }

  fun setItem(slot: Int, item: ItemStack, force: Boolean = false) {
    if (!hasItem(slot)) {
      inventory.setItem(slot, item)
      return
    }

    if (!force) {
      throw SlotAlreadyTakenException(slot)
    }

    inventory.setItem(slot, item)
  }

  fun setItem(slot: Int, item: ItemStack, action: InventoryHandler) {
    setItem(slot, item)
    holder.handleActionHandler(slot, action)
  }

  fun open(player: Player) {
    player.openInventory(inventory)
  }
}