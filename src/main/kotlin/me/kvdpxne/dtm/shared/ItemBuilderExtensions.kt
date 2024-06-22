package me.kvdpxne.dtm.shared

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun Material.toBuilder(): ItemBuilder {
  return ItemBuilder().item(this)
}

fun ItemStack.toBuilder(): ItemBuilder {
  return ItemBuilder().item(this)
}