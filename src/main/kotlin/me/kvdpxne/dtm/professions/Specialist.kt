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
import me.kvdpxne.dtm.shared.bukkit.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

fun createSpecialist(): Profession = ProfessionBuilder()
  .name("specialist")
  .displayName("Specjalista")
  .items(
    // Armor
    slotHelmet(Material.IRON_HELMET),
    slotChestplate(Material.IRON_CHESTPLATE),
    slotLeggings(Material.LEATHER_LEGGINGS),
    slotBoots(Material.LEATHER_BOOTS),

    // Weapons
    slotItem({
      Material.STONE_SWORD.toBuilder()
        .enchantment(Enchantment.DAMAGE_ALL, 1)
        .unbreakable()
        .build()
    }, index = 0),
    slotItem({
      Material.SLIME_BALL.toBuilder()
        .lore("")
        .build()
    }, index = 1),

    // Tools
    slotItem(ITEM_TOOL_AXE, index = 2),
    slotItem(ITEM_TOOL_PICKAXE, index = 3),

    slotItem(Material.POTION, 2, 7),
    slotItem(Material.COBBLESTONE, 40, 8),
  )
  .icon(
    Material.STONE_SWORD.toBuilder()
      .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
      .build()
  )
  .disabled()
  .build()