package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.minecraft.bukkit.asItem
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.shared.minecraft.bukkit.isArmor
import me.kvdpxne.dtm.shared.minecraft.bukkit.isIngot
import me.kvdpxne.dtm.shared.minecraft.bukkit.isTool
import me.kvdpxne.dtm.shared.minecraft.bukkit.isWeapon
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent

/**
 * @since 0.1.0
 */
object PlayerCraftItemListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerCraftItem(
    event: CraftItemEvent
  ) {
    if (event.isCancelled) {
      return
    }

    //
    val type: Material = event.recipe.result.type

    //
    if (type.isWeapon() || type.isArmor() || type.isIngot()) {
      event.cancel()
      event.inventory.result = Material.AIR.asItem()
      return
    }

    //
    if (type.isTool()) {
      event.inventory.result = ItemsClipboard.makeTool(type)
      return
    }
  }
}