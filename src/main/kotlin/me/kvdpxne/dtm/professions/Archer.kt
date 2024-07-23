package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

fun createArcher(): Profession = ProfessionBuilder()
  .name("archer")
  .displayName("Łucznik")
  .items(
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
  )
  .icon(Material.BOW)
  .ability(40)
  .enabled()
  .build()