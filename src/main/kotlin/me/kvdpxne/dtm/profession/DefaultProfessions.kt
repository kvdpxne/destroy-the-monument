package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.gui.slotLeggings
import me.kvdpxne.dtm.shared.Attributes
import me.kvdpxne.dtm.shared.asItem
import me.kvdpxne.dtm.shared.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private val TOOL_AXE = Material.IRON_AXE.toBuilder()
  .lore(
    "&7Tools deal &c&lLESS DAMAGE &7than your main",
    "&7weapon because they should be used to",
    "&7interact with the game map and not used",
    "&7for dueling between players."
  )
  .enchantment(Enchantment.DIG_SPEED, 1)
  .attribute(Attributes.ATTACK_DAMAGE, 1.75)
  .unbreakable()
  .build()

private val TOOL_PICKAXE = Material.DIAMOND_PICKAXE.toBuilder()
  .lore(
    "&7Tools deal &c&lLESS DAMAGE &7than your main",
    "&7weapon because they should be used to",
    "&7interact with the game map and not used",
    "&7for dueling between players."
  )
  .attribute(Attributes.ATTACK_DAMAGE, 1.15)
  .unbreakable()
  .build()

fun archer() = Profession(
  "archer",
  "Łucznik",
  mutableListOf(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.LEATHER_CHESTPLATE),
    slotBoots(Material.LEATHER_BOOTS),

    // Weapons
    slotItem({
      Material.WOOD_SWORD.toBuilder()
        .unbreakable()
        .build()
    }, index = 0),
    slotItem({
      Material.BOW.toBuilder()
        .enchantment(Enchantment.ARROW_DAMAGE, 2)
        .enchantment(Enchantment.ARROW_INFINITE, 1)
        .unbreakable()
        .build()
    }, index = 1),

    // Tools
    slotItem(TOOL_AXE, index = 2),
    slotItem(TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 30, 8),
    slotItem(Material.ARROW, 1, 17)
  ),
  Material.BOW.asItem()
)

fun engineer() = Profession(
  "engineer",
  "Technik",
  mutableListOf(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.LEATHER_CHESTPLATE),
    slotBoots(Material.LEATHER_BOOTS),

    // Weapons
    slotItem({
      Material.STONE_SWORD.toBuilder()
        .unbreakable()
        .build()
    }, index = 0),

    // Tools
    slotItem(TOOL_AXE, index = 1),
    slotItem(TOOL_PICKAXE, index = 2),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.COBBLESTONE, 50, 8),
  ),
  Material.COBBLESTONE.asItem()
)

fun knight() = Profession(
  "knight",
  "Rycerz",
  mutableListOf(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.IRON_CHESTPLATE),
    slotLeggings(Material.LEATHER_LEGGINGS),
    slotBoots(Material.IRON_BOOTS),

    // Weapons
    slotItem({
      Material.IRON_SWORD.toBuilder()
        .unbreakable()
        .build()
    }, index = 0),

    // Tools
    slotItem(TOOL_AXE, index = 1),
    slotItem(TOOL_PICKAXE, index = 2),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 40, 8),
  ),
  Material.IRON_SWORD.asItem(),
  PotionEffect(PotionEffectType.SLOW, Int.MAX_VALUE, 0)
)

fun pyro() = Profession(
  "pyro",
  "Piromanta",
  mutableListOf(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotBoots(Material.IRON_BOOTS),

    // Weapons
    slotItem({
      Material.WOOD_SWORD.toBuilder()
        .enchantment(Enchantment.FIRE_ASPECT, 1)
        .unbreakable()
        .build()
    }, index = 0),
    slotItem({
      Material.BOW.toBuilder()
        .enchantment(Enchantment.ARROW_FIRE, 1)
        .enchantment(Enchantment.ARROW_INFINITE, 1)
        .build()
    }, index = 1),

    // Tools
    slotItem(TOOL_AXE, index = 2),
    slotItem(TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 20, 8),
    slotItem(Material.ARROW, 1, 17)
  ),
  Material.FLINT_AND_STEEL.asItem(),
  PotionEffect(PotionEffectType.FIRE_RESISTANCE, Int.MAX_VALUE, 0)
)

fun scout() = Profession(
  "scout",
  "Zwiadowca",
  mutableListOf(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotBoots(Material.LEATHER_BOOTS),

    // Weapons
    slotItem({
      Material.WOOD_SWORD.toBuilder()
        .enchantment(Enchantment.DAMAGE_ALL, 1)
        .unbreakable()
        .build()
    }, index = 0),
    slotItem({
      Material.FISHING_ROD.toBuilder()
        .unbreakable()
        .build()
    }, index = 1),

    // Tools
    slotItem(TOOL_AXE, index = 2),
    slotItem(TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 45, 8)
  ),
  Material.FISHING_ROD.asItem(),
  PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 0)
)

fun medic() = Profession(
  "medic",
  "Medyk",
  mutableListOf(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.IRON_CHESTPLATE),
    slotBoots(Material.LEATHER_BOOTS),

    // Weapons
    slotItem({
      Material.STONE_SWORD.toBuilder()
        .unbreakable()
        .build()
    }, index = 0),

    // Tools
    slotItem(TOOL_AXE, index = 1),
    slotItem(TOOL_PICKAXE, index = 2),

    slotItem({
      val potion = Material.POTION.asItem()
      val meta = potion.itemMeta as PotionMeta
      meta.addCustomEffect(PotionEffect(PotionEffectType.SPEED, 40, 0), true)
      potion
    }, index = 3),
    slotItem({
      val potion = Material.POTION.asItem()
      val meta = potion.itemMeta as PotionMeta
      meta.addCustomEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 4), true)
      potion
    }, index = 4),
    slotItem(Material.POTION, 4, 7),
    slotItem(Material.WOOD, 30, 8)
  ),
  Material.POTION.asItem()
)

fun defender() = Profession(
  "defender",
  "Obrońca",
  mutableListOf(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.IRON_CHESTPLATE),
    slotLeggings(Material.LEATHER_LEGGINGS),
    slotBoots(Material.DIAMOND_BOOTS),

    // Weapons
    slotItem({
      Material.IRON_SWORD.toBuilder()
        .enchantment(Enchantment.KNOCKBACK, 1)
        .unbreakable()
        .build()
    }, index = 0),
    slotItem({
      Material.BOW.toBuilder()
        .enchantment(Enchantment.ARROW_KNOCKBACK, 1)
        .enchantment(Enchantment.ARROW_INFINITE, 1)
        .unbreakable()
        .build()
    }, index = 1),

    // Tools
    slotItem(TOOL_AXE, index = 2),
    slotItem(TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 10, 8),
    slotItem(Material.ARROW, 1, 17)
  ),
  Material.IRON_SWORD.asItem(),
  PotionEffect(PotionEffectType.SLOW, Int.MAX_VALUE, 0)
)