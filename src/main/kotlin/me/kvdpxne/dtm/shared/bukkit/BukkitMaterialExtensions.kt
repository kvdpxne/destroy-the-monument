package me.kvdpxne.dtm.shared.bukkit

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun Material.asItem(): ItemStack {
  return ItemStack(this)
}

fun Material.toBuilder(): ItemBuilder {
  return ItemBuilder().item(this)
}

fun Material.hasInventory(): Boolean {
  return when (this) {
    Material.CHEST,
    Material.STORAGE_MINECART,
    Material.TRAPPED_CHEST,
    Material.ENDER_CHEST,
//    Material.WORKBENCH,
    Material.FURNACE,
    Material.BURNING_FURNACE,
    Material.DISPENSER,
    Material.DROPPER,
    Material.ENCHANTMENT_TABLE,
    Material.BREWING_STAND,
    Material.BEACON,
    Material.ANVIL,
    Material.HOPPER,
    Material.HOPPER_MINECART -> true

    else -> false
  }
}

fun Material.isRich(): Boolean {
  return when (this) {
    Material.QUARTZ_BLOCK,
    Material.QUARTZ_ORE,
    Material.EMERALD_BLOCK,
    Material.EMERALD_ORE,
    Material.DIAMOND_BLOCK,
    Material.DIAMOND_ORE,
    Material.GOLD_BLOCK,
    Material.GOLD_ORE,
    Material.LAPIS_BLOCK,
    Material.LAPIS_ORE,
    Material.REDSTONE_BLOCK,
    Material.REDSTONE_ORE,
    Material.GLOWING_REDSTONE_ORE,
    Material.REDSTONE_WIRE,
    Material.REDSTONE,
    Material.IRON_BLOCK,
    Material.IRON_ORE,
    Material.COAL_BLOCK,
    Material.COAL_ORE,
    Material.ENDER_CHEST,
    Material.ENCHANTMENT_TABLE -> true

    else -> false
  }
}

fun Material.isNature(): Boolean {
  return when (this) {
    Material.SAPLING,
    Material.LONG_GRASS,
    Material.DEAD_BUSH,
    Material.YELLOW_FLOWER,
    Material.RED_ROSE,
    Material.BROWN_MUSHROOM,
    Material.RED_MUSHROOM,
    Material.CACTUS,
    Material.VINE,
    Material.WATER_LILY,
    Material.DOUBLE_PLANT,

      //
    Material.LEAVES,
    Material.LEAVES_2 -> true

    else -> false
  }
}

fun Material.isMonument(): Boolean {
  return Material.OBSIDIAN == this
}
