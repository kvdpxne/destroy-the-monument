package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.gui.slotLeggings
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_AXE
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_PICKAXE
import me.kvdpxne.dtm.shared.minecraft.bukkit.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

fun createAssassin(): Profession = ProfessionBuilder()
  .name("assassin")
  .displayName("Asasyn")
  .items(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.LEATHER_CHESTPLATE),
    slotLeggings(Material.LEATHER_LEGGINGS),
    slotBoots(Material.LEATHER_BOOTS),

    // Weapons
    slotItem({
      Material.WOOD_SWORD.toBuilder()
        .enchantment(Enchantment.DAMAGE_ALL, 2)
        .unbreakable()
        .build()
    }, index = 0),
    slotItem({
      Material.STICK.toBuilder()
        .lore(
          "&7Hitting an opponent in the back",
          "&7deals &c&lHUGE &7damage."
        )
        .enchantment(Enchantment.DAMAGE_ALL, 1)
        .unbreakable()
        .build()
    }, index = 1),

    // Tools
    slotItem(ITEM_TOOL_AXE, index = 2),
    slotItem(ITEM_TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.WOOD, 10, 8),
  )
  .icon(
    Material.STICK.toBuilder()
      .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
      .build()
  )
  .disabled()
  .build()