package me.kvdpxne.dtm.shared.material

import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.shared.item.ItemBuilder
import me.kvdpxne.dtm.shared.item.hasDurability
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * Extension function for [Material] that converts it into an [ItemStack] with
 * a specified amount.
 *
 * This function provides a simple way to create an [ItemStack] of the given
 * material and quantity.
 *
 * Ensures the amount is at least `1`.
 *
 * @param amount The quantity of items in the stack. Defaults to `1`.
 *
 * @return A new [ItemStack] containing the specified material and amount.
 *
 * @throws IllegalArgumentException if `amount` is less than 1.
 *
 * @since 0.1.0
 */
fun Material.asItem(
  amount: Int = 1,
): ItemStack {
  require(1 <= amount) {
    "The amount cannot be less than 1."
  }

  return ItemStack(this, amount)
}

/**
 * @since 0.1.0
 */
fun Material.toBuilder(): ItemBuilder {
  return ItemBuilder.begin(ItemStack(this))
}

fun Material.hasDurability(): Boolean {
  return 0 < this.maxDurability && 1 == this.maxStackSize
}

/**
 * @since 0.1.0
 */
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

fun Material.isPlant(): Boolean {
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
 * Extension function for [Material] that checks if the material represents a
 * tool.
 *
 * This function identifies common tool items, such as pickaxes, axes, hoes,
 * and shears, across various material types (wood, stone, iron, gold, diamond).
 *
 * @return `true` if the material is a tool; `false` otherwise.
 *
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
 * Extension function for [Material] that checks if the material represents a
 * primary weapon.
 *
 * This function identifies standard weapon items, including swords of various
 * materials and bows.
 *
 * @return `true` if the material is a primary weapon; `false` otherwise.
 *
 * @since 0.1.0
 */
fun Material.isWeapon(): Boolean {
  return when (this) {
    Material.WOOD_SWORD,
    Material.STONE_SWORD,
    Material.IRON_SWORD,
    Material.GOLD_SWORD,
    Material.DIAMOND_SWORD,
    Material.BOW -> true

    else -> false
  }
}

/**
 * Extension function for [Material] that checks if the material represents a
 * subsidiary weapon.
 *
 * This function identifies non-primary weapon items that can be used in combat
 * situations, such as fishing rods, flint and steel, and firework charges.
 *
 * @return `true` if the material is a subsidiary weapon; `false` otherwise.
 *
 * @since 0.1.0
 */
fun Material.isSubsidiaryWeapon(): Boolean {
  return when (this) {
    Material.FISHING_ROD,
    Material.FLINT_AND_STEEL,
    Material.FIREWORK_CHARGE -> true

    else -> false
  }
}

/**
 * Extension function for [Material] that checks if the material represents a
 * piece of leather armor.
 *
 * Identifies leather armor items such as helmet, chestplate, leggings, and
 * boots.
 *
 * @return `true` if the material is a piece of leather armor; `false`
 *         otherwise.
 *
 * @since 0.1.0
 */
fun Material.isLeatherArmor(): Boolean {
  return when (this) {
    Material.LEATHER_HELMET,
    Material.LEATHER_CHESTPLATE,
    Material.LEATHER_LEGGINGS,
    Material.LEATHER_BOOTS -> true

    else -> false
  }
}

/**
 * Extension function for [Material] that checks if the material represents any
 * type of armor.
 *
 * This function recognizes all standard armor types, including leather,
 * chainmail, iron, gold, and diamond armor.
 *
 * It also uses [Material.isLeatherArmor] for leather-specific checks.
 *
 * @return `true` if the material is a piece of armor; `false` otherwise.
 *
 * @since 0.1.0
 */
fun Material.isArmor(): Boolean {
  return this.isLeatherArmor() || when (this) {
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
  return Configuration.MONUMENT_TYPE == this
}
