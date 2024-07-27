package me.kvdpxne.dtm.shared.bukkit

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun ItemStack.toBuilder(): ItemBuilder {
  return ItemBuilder.begin(this)
}

fun ItemStack?.isNullOrTypeAir(): Boolean {
  return null == this || Material.AIR == this.type
}