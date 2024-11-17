package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.shared.item.ItemsClipboard
import me.kvdpxne.dtm.shared.material.isArmor
import me.kvdpxne.dtm.shared.material.isIngot
import me.kvdpxne.dtm.shared.material.isTool
import me.kvdpxne.dtm.shared.material.isWeapon
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack

/**
 * @since 0.1.0
 */
object PlayerPrepareCraftItemListener : Listener {

  private val AIR: ItemStack by lazy {
    ItemStack(Material.AIR)
  }

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerPrepareCraftItem(
    event: PrepareItemCraftEvent
  ) {
    val world: World = event.view.player.world
    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)

    if (world != arena?.map?.world) {
      return
    }

    //
    val type: Material = event.recipe.result.type

    //
    if (type.isWeapon() || type.isArmor() || type.isIngot()) {
      event.inventory.result = AIR
      return
    }

    //
    if (type.isTool()) {
      event.inventory.result = ItemsClipboard.makeTool(type)
      return
    }
  }
}