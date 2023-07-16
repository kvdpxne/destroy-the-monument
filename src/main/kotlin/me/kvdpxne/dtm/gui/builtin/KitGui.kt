package me.kvdpxne.dtm.gui.builtin

import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.Rows
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class KitGui : Gui("Wybór klasy", Rows.TWO) {

  init {
    val item = ItemStack(Material.STAINED_CLAY, 1, 4)
    val meta = item.itemMeta

    meta.displayName = " "
    item.itemMeta = meta

    this.setItem(0, item)
    this.setItem(1, item)
    this.setItem(2, item)
    this.setItem(3, item)
    this.setItem(4, item)
    this.setItem(5, item)

    item.durability = 14

    this.setItem(6, item)
    this.setItem(7, item)
    this.setItem(8, item)

    this.setItem(9, ItemStack(Material.BOW))
    this.setItem(10, ItemStack(Material.IRON_SWORD))
    this.setItem(11, ItemStack(Material.COBBLESTONE))
    this.setItem(12, ItemStack(Material.FISHING_ROD))
    this.setItem(13, ItemStack(Material.POTION))
    this.setItem(14, ItemStack(Material.FLINT_AND_STEEL))
    this.setItem(15, ItemStack(Material.GOLD_SWORD))
    this.setItem(16, ItemStack(Material.STICK))
    this.setItem(17, ItemStack(Material.SHEARS))
  }
}