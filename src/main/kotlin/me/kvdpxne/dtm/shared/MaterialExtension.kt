package me.kvdpxne.dtm.shared

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun Material.asItem(): ItemStack {
  return ItemStack(this)
}