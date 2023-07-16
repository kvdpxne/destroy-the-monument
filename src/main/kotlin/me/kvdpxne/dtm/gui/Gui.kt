package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.kit.SlotAlreadyTakenException
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

open class Gui(displayName: String, rows: Rows) {

  private val holder = GuiHolder()
  private val inventory = Bukkit.createInventory(holder, rows.size, displayName)

  init {
    holder.inventory = this.inventory
  }

  fun setItem(slot: Int, item: ItemStack) {
    inventory.getItem(slot) ?: throw SlotAlreadyTakenException(slot)
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