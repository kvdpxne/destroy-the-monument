package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_AXE
import me.kvdpxne.dtm.shared.ItemsClipboard.ITEM_TOOL_PICKAXE
import me.kvdpxne.dtm.shared.minecraft.bukkit.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.potion.PotionEffectType

fun createPyro(): Profession = ProfessionBuilder()
  .name("pyro")
  .displayName("Piromanta")
  .items(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotBoots(Material.IRON_BOOTS),

    // Weapons
    slotItem({
      Material.WOOD_SWORD.toBuilder()
        .enchantment(Enchantment.FIRE_ASPECT, 1)
        .unbreakable()
        .build()
    }, index = 0),
    slotItem({
      Material.BOW.toBuilder()
        .enchantment(Enchantment.ARROW_FIRE, 1)
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
  .icon(Material.FLINT_AND_STEEL)
  .effect(PotionEffectType.FIRE_RESISTANCE)
  .ability(40, true)
  .enabled()
  .build()