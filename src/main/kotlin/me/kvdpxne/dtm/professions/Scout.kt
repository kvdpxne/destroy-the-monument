package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_AXE
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_PICKAXE
import me.kvdpxne.dtm.shared.bukkit.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffectType

fun createScout(): Profession = ProfessionBuilder()
  .name("scout")
  .displayName("Zwiadowca")
  .items(
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
    slotItem(ITEM_TOOL_AXE, index = 2),
    slotItem(ITEM_TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 45, 8)
  )
  .icon(Material.FISHING_ROD)
  .effect(PotionEffectType.SPEED, 1)
  .ability(30, false) {
    it.allowFlight = true
  }
  .enabled()
  .build()