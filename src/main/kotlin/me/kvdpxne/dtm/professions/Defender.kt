package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.profession.slotBoots
import me.kvdpxne.dtm.profession.slotChestplate
import me.kvdpxne.dtm.profession.slotHelmet
import me.kvdpxne.dtm.profession.slotItem
import me.kvdpxne.dtm.profession.slotLeggings
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.item.ItemsClipboard.ITEM_TOOL_AXE
import me.kvdpxne.dtm.shared.item.ItemsClipboard.ITEM_TOOL_PICKAXE
import me.kvdpxne.dtm.shared.material.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffectType

fun createDefender(): Profession = ProfessionBuilder()
  .name("defender")
  .displayName("Obrońca")
  .items(
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
    slotItem(ITEM_TOOL_AXE, index = 2),
    slotItem(ITEM_TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 20, 8),
    slotItem(Material.ARROW, 1, 17)
  )
  .icon(
    Material.IRON_SWORD.toBuilder()
      .lore(
        "",
        "&7- Pancernik",
        "&7- Budowniczy",
        "&7- Strzelec"
      )
      .enchantmentEffect()
      .build()
  )
  .effect(PotionEffectType.SLOW)
  .disabled()
  .build()