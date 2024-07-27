package me.kvdpxne.dtm.shared.bukkit

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun Material.asItem(): ItemStack {
  return ItemStack(this)
}

fun Material.toBuilder(): ItemBuilder {
  return ItemBuilder.begin(this.asItem())
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

fun Material.isIngot(): Boolean {
  return when (this) {
    Material.DIAMOND_BLOCK,
    Material.DIAMOND,
    Material.GOLD_BLOCK,
    Material.GOLD_INGOT,
    Material.GOLD_NUGGET,
    Material.IRON_BLOCK,
    Material.IRON_INGOT,
    Material.REDSTONE_BLOCK,
    Material.REDSTONE -> true

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

/**
 * @since 0.1.0
 */
fun Material.isTool(): Boolean {
  return when (this) {
    Material.WOOD_SPADE,
    Material.WOOD_PICKAXE,
    Material.WOOD_AXE,
    Material.WOOD_HOE,
    Material.STONE_SPADE,
    Material.STONE_PICKAXE,
    Material.STONE_AXE,
    Material.STONE_HOE,
    Material.IRON_SPADE,
    Material.IRON_PICKAXE,
    Material.IRON_AXE,
    Material.IRON_HOE,
    Material.GOLD_SPADE,
    Material.GOLD_PICKAXE,
    Material.GOLD_AXE,
    Material.GOLD_HOE,
    Material.DIAMOND_SPADE,
    Material.DIAMOND_PICKAXE,
    Material.DIAMOND_AXE,
    Material.DIAMOND_HOE,
    Material.SHEARS -> true

    else -> false
  }
}

/**
 * @since 0.1.0
 */
fun Material.isWeapon(): Boolean {
  return when (this) {
    Material.WOOD_SWORD,
    Material.STONE_SWORD,
    Material.IRON_SWORD,
    Material.GOLD_SWORD,
    Material.DIAMOND_SWORD,
    Material.BOW,
    Material.FISHING_ROD,
    Material.FLINT_AND_STEEL -> true

    else -> false
  }
}

/**
 * @since 0.1.0
 */
fun Material.isArmor(): Boolean {
  return when (this) {
    Material.LEATHER_HELMET,
    Material.LEATHER_CHESTPLATE,
    Material.LEATHER_LEGGINGS,
    Material.LEATHER_BOOTS,
    Material.CHAINMAIL_HELMET,
    Material.CHAINMAIL_CHESTPLATE,
    Material.CHAINMAIL_LEGGINGS,
    Material.CHAINMAIL_BOOTS,
    Material.IRON_HELMET,
    Material.IRON_CHESTPLATE,
    Material.IRON_LEGGINGS,
    Material.IRON_BOOTS,
    Material.GOLD_HELMET,
    Material.GOLD_CHESTPLATE,
    Material.GOLD_LEGGINGS,
    Material.GOLD_BOOTS,
    Material.DIAMOND_HELMET,
    Material.DIAMOND_CHESTPLATE,
    Material.DIAMOND_LEGGINGS,
    Material.DIAMOND_BOOTS -> true

    else -> false
  }
}

fun Material.isMonument(): Boolean {
  return Material.OBSIDIAN == this
}
