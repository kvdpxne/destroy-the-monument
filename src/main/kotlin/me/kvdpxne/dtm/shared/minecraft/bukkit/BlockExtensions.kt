package me.kvdpxne.dtm.shared.minecraft.bukkit

import org.bukkit.Material
import org.bukkit.block.Block

fun Block.isRich(): Boolean {
  return this.type.isRich()
}

fun Block.isPlant(): Boolean {
  return this.type.isPlant()
}

fun Block.hasInventory(): Boolean {
  return this.type.hasInventory()
}

fun Block.isMonument(): Boolean {
  return this.type.isMonument()
}

/**
 * Changes the type of the block to air, effectively making it disappear from
 * the world.
 *
 * This function sets the block's type to `Material.AIR`, removing it visually
 * and functionally.
 *
 * @receiver Block - the block that will disappear.
 * @since 0.1.0
 */
fun Block.disappear() {
  this.type = Material.AIR
}