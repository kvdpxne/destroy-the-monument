package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.shared.bukkit.hasInventory
import me.kvdpxne.dtm.shared.bukkit.isNature
import me.kvdpxne.dtm.shared.bukkit.isRich
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

/**
 * @since 0.1.0
 */
object EntityExplodeListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handleEntityExplode(
    event: EntityExplodeEvent
  ) {
    if (event.isCancelled) {
      return
    }

    for (block: Block in event.blockList().toTypedArray()) {
      if (block.hasInventory() || block.isRich() || block.isNature()) {
        block.type = Material.AIR
        block.drops.clear()
      }
    }
  }
}