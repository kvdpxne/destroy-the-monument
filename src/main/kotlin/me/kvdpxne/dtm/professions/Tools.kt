package me.kvdpxne.dtm.professions

import me.kvdpxne.dtm.shared.bukkit.Attributes
import me.kvdpxne.dtm.shared.bukkit.toBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

internal val TOOL_AXE = Material.IRON_AXE.toBuilder()
  .lore(
    "&7Tools deal &c&lLESS DAMAGE &7than your main",
    "&7weapon because they should be used to",
    "&7interact with the game map and not used",
    "&7for dueling between players."
  )
  .enchantment(Enchantment.DIG_SPEED, 1)
  .attribute(Attributes.ATTACK_DAMAGE, 1.75)
  .unbreakable()
  .build()

internal val TOOL_PICKAXE = Material.DIAMOND_PICKAXE.toBuilder()
  .lore(
    "&7Tools deal &c&lLESS DAMAGE &7than your main",
    "&7weapon because they should be used to",
    "&7interact with the game map and not used",
    "&7for dueling between players."
  )
  .attribute(Attributes.ATTACK_DAMAGE, 1.15)
  .unbreakable()
  .build()