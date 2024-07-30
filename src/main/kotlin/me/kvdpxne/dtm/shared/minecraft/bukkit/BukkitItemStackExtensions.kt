package me.kvdpxne.dtm.shared.minecraft.bukkit

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun ItemStack.toBuilder(): ItemBuilder {
  return ItemBuilder.begin(this)
}

fun ItemStack?.isNullOrTypeAir(): Boolean {
  return null == this || Material.AIR == this.type
}

/**
 *
 */
fun ItemStack.hasDurability(): Boolean {
  return 0 < this.type.maxDurability && 1 == this.type.maxStackSize
}

/**
 *
 */
fun ItemStack.isLeatherArmor(): Boolean {
  return this.type.isLeatherArmor()
}

/**
 *
 */
fun ItemStack.isArmor(): Boolean {
  return this.type.isArmor()
}