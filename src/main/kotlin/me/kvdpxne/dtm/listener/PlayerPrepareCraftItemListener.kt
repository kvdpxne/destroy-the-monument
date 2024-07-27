package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.bukkit.asItem
import me.kvdpxne.dtm.shared.bukkit.isArmor
import me.kvdpxne.dtm.shared.bukkit.isIngot
import me.kvdpxne.dtm.shared.bukkit.isTool
import me.kvdpxne.dtm.shared.bukkit.isWeapon
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent

/**
 * @since 0.1.0
 */
object PlayerPrepareCraftItemListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerPrepareCraftItem(
    event: PrepareItemCraftEvent
  ) {



    //
    val type: Material = event.recipe.result.type

    //
    if (type.isWeapon() || type.isArmor() || type.isIngot()) {
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