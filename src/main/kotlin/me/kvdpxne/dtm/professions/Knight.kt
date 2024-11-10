package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.gui.slotLeggings
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.item.ItemsClipboard.ITEM_TOOL_AXE
import me.kvdpxne.dtm.shared.item.ItemsClipboard.ITEM_TOOL_PICKAXE
import me.kvdpxne.dtm.shared.material.toBuilder
import org.bukkit.Material
import org.bukkit.potion.PotionEffectType

fun createKnight(): Profession = ProfessionBuilder()
  .name("knight")
  .displayName("Rycerz")
  .items(
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
    slotItem(ITEM_TOOL_AXE, index = 1),
    slotItem(ITEM_TOOL_PICKAXE, index = 2),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 40, 8),
  )
  .icon(
    Material.IRON_SWORD.toBuilder()
      .lore(
        "",
        "&7- Tarczownik",
        "&7- Czołg",
        "&7- Wojownik"
      )
      .build()
  )
  .effect(PotionEffectType.SLOW)
  .disabled()
  .build()