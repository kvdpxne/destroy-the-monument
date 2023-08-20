package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.shared.asItem
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun archer() = Profession(
  "archer",
  "Łucznik",
  mutableListOf(
    slotItem(Material.LEATHER_HELMET, index = 39),
    slotItem(Material.LEATHER_CHESTPLATE, index = 38),
    slotItem(Material.LEATHER_BOOTS, index = 36),

    slotItem(Material.WOOD_SWORD, index = 0),
    slotItem({
      val item = ItemStack(Material.BOW)
      item.addEnchantment(Enchantment.ARROW_DAMAGE, 2)
      item.addEnchantment(Enchantment.ARROW_INFINITE, 1)
      item
    }, index = 1),
    slotItem({
      val item = ItemStack(Material.IRON_AXE)
      item.addEnchantment(Enchantment.DIG_SPEED, 1)
      item
    }, index = 2),
    slotItem(Material.DIAMOND_PICKAXE, index = 3),
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
    slotItem(Material.LEATHER_HELMET, index = 39),
    slotItem(Material.LEATHER_CHESTPLATE, index = 38),
    slotItem(Material.LEATHER_BOOTS, index = 36),

    slotItem(Material.STONE_SWORD, index = 0),
    slotItem({
      val item = ItemStack(Material.IRON_AXE)
      item.addEnchantment(Enchantment.DIG_SPEED, 1)
      item
    }, index = 1),
    slotItem(Material.DIAMOND_PICKAXE, index = 2),
    slotItem(Material.POTION, 2, 7),
    slotItem(Material.COBBLESTONE, 50, 8),
  ),
  Material.COBBLESTONE.asItem()
)

fun knight() = Profession(
  "knight",
  "Rycerz",
  mutableListOf(
    slotItem(Material.LEATHER_HELMET, index = 39),
    slotItem(Material.IRON_CHESTPLATE, index = 38),
    slotItem(Material.LEATHER_LEGGINGS, index = 37),
    slotItem(Material.IRON_BOOTS, index = 36),

    slotItem(Material.IRON_SWORD, index = 0),
    slotItem(Material.IRON_AXE, index = 1),
    slotItem(Material.DIAMOND_PICKAXE, index = 2),
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
    slotItem(Material.LEATHER_HELMET, index = 39),
    slotItem(Material.IRON_BOOTS, index = 36),

    slotItem({
      val item = Material.WOOD_SWORD.asItem()
      item.addEnchantment(Enchantment.FIRE_ASPECT, 1)
      item
    }, index = 0),
    slotItem({
      val item = Material.BOW.asItem()
      item.addEnchantment(Enchantment.ARROW_FIRE, 1)
      item.addEnchantment(Enchantment.ARROW_INFINITE, 1)
      item
    }, index = 1),
    slotItem(Material.IRON_AXE, index = 2),
    slotItem(Material.DIAMOND_PICKAXE, index = 3),
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
    slotItem(Material.LEATHER_HELMET, index = 39),
    slotItem(Material.LEATHER_BOOTS, index = 36),

    slotItem({
      val item = ItemStack(Material.WOOD_SWORD)
      item.addEnchantment(Enchantment.DAMAGE_ALL, 1)
      item
    }, index = 0),
    slotItem(Material.FISHING_ROD, index = 1),
    slotItem({
      val item = ItemStack(Material.IRON_AXE)
      item.addEnchantment(Enchantment.DIG_SPEED, 2)
      item
    }, index = 2),
    slotItem(Material.DIAMOND_PICKAXE, index = 3),
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
    slotItem(Material.LEATHER_HELMET, index = 39),
    slotItem(Material.LEATHER_CHESTPLATE, index = 38),
    slotItem(Material.LEATHER_BOOTS, index = 36),

    slotItem(Material.STONE_SWORD, index = 0),
    slotItem({
      val item = ItemStack(Material.IRON_AXE)
      item.addEnchantment(Enchantment.DIG_SPEED, 2)
      item
    }, index = 1),
    slotItem(Material.DIAMOND_PICKAXE, index = 2),
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