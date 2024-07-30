package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_AXE
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_PICKAXE
import me.kvdpxne.dtm.shared.minecraft.bukkit.toBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

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
    slotItem(ITEM_TOOL_AXE, index = 1),
    slotItem(ITEM_TOOL_PICKAXE, index = 2),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.COBBLESTONE, 50, 8),
  )
  .icon(Material.COBBLESTONE)
  .ability(20, true) {
    it.inventory.addItem(ItemStack(Material.COBBLESTONE, 15))
  }
  .enabled()
  .build()