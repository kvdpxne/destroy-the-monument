package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.gui.slotBoots
import me.kvdpxne.dtm.gui.slotChestplate
import me.kvdpxne.dtm.gui.slotHelmet
import me.kvdpxne.dtm.gui.slotItem
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionBuilder
import me.kvdpxne.dtm.shared.asItem
import me.kvdpxne.dtm.shared.toBuilder
import org.bukkit.Material
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

fun createMedic(): Profession = ProfessionBuilder()
  .name("medic")
  .displayName("Medyk")
  .items(
    // Armor
    slotHelmet(Material.LEATHER_HELMET),
    slotChestplate(Material.IRON_CHESTPLATE),
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

    slotItem({
      val potion = Material.POTION.asItem()
      val meta = potion.itemMeta as PotionMeta
      meta.addCustomEffect(PotionEffect(PotionEffectType.SPEED, 40, 0), true)
      potion
    }, index = 3),
    slotItem({
      val potion = Material.POTION.asItem()
      val meta = potion.itemMeta as PotionMeta
      meta.addCustomEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 4), true)
      potion
    }, index = 4),
    slotItem(Material.POTION, 4, 7),
    slotItem(Material.WOOD, 30, 8)
  )
  .icon(Material.POTION)
  .disabled()
  .build()