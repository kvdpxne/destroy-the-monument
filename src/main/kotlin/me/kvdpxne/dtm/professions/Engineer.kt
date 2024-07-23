package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.bukkit.toBuilder
import org.bukkit.Material

fun createEngineer(): Profession = ProfessionBuilder()
  .name("engineer")
  .displayName("Technik")
  .items(
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
  )
  .icon(Material.COBBLESTONE)
  .enabled()
  .build()