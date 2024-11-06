package me.kvdpxne.dtm.shared.item

import me.kvdpxne.dtm.shared.material.hasDurability
import me.kvdpxne.dtm.shared.material.isArmor
import me.kvdpxne.dtm.shared.material.isLeatherArmor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @since 0.1.0
 */
fun ItemStack.toBuilder(): ItemBuilder {
  return ItemBuilder.begin(this)
}

/**
 * @since 0.1.0
 */
fun ItemStack?.isNullOrTypeAir(): Boolean {
  return null == this || Material.AIR == this.type
}


/**
 * @since 0.1.0
 */
fun ItemStack.hasDurability(): Boolean {
  return this.type.hasDurability()
}

/**
 * @since 0.1.0
 */
fun ItemStack.isLeatherArmor(): Boolean {
  return this.type.isLeatherArmor()
}

/**
 * @since 0.1.0
 */
fun ItemStack.isArmor(): Boolean {
  return this.type.isArmor()
}