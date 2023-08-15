package me.kvdpxne.dtm.profession

import me.kvdpxne.dtm.gui.slotItem
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun archer() = Profession(
  "archer",
  "Archer",
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
  ),
  ItemStack(Material.BOW)
)

fun scout() = Profession(
  "Scout",
  "Scout",
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
  ItemStack(Material.FISHING_ROD)
)
//
//fun scout() = Profession(
//  name = "scout",
//  displayName = "Scout",
//  kit = Kit(
//    name = "scout",
//    displayName = "Scout",
//    items = mutableListOf(
//      slotItem(Material.LEATHER_HELMET, index = 39),
//      slotItem(Material.LEATHER_BOOTS, index = 36),
//
//      slotItem({
//        val item = ItemStack(Material.WOOD_SWORD)
//        item.addEnchantment(Enchantment.DAMAGE_ALL, 1)
//        item
//      }, index = 0),
//      slotItem(Material.FISHING_ROD, index = 1),
//      slotItem({
//        val item = ItemStack(Material.IRON_AXE)
//        item.addEnchantment(Enchantment.DIG_SPEED, 2)
//        item
//      }, index = 2),
//      slotItem(Material.DIAMOND_PICKAXE, index = 3),
//      slotItem(Material.POTION, 2, 7),
//      slotItem(Material.WOOD, 45, 8)
//    ),
//    icon = ItemStack(Material.FISHING_ROD)
//  )
//)