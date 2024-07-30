package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaService
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.game.RevivalPosition.Companion.RADIUS_OF_EXPLOSION_INTERACTION
import me.kvdpxne.dtm.shared.basics.isNear
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.shared.minecraft.bukkit.hasInventory
import me.kvdpxne.dtm.shared.minecraft.bukkit.isNature
import me.kvdpxne.dtm.shared.minecraft.bukkit.isRich
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

/**
 * @since 0.1.0
 */
object EntityExplodeListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(priority = EventPriority.HIGH)
  fun handleEntityExplode(
    event: EntityExplodeEvent
  ) {
    if (event.isCancelled) {
      return
    }

    // The object of location an entity explosion
    val location: Location = event.location

    //
    val arena: Arena = ArenaService.findLoadedArenaByWorldIdentifier(location.world.uid) ?: return

    for (revivalPosition: RevivalPosition in arena.revivalPositions) {
      if (!revivalPosition.isNear(location, RADIUS_OF_EXPLOSION_INTERACTION)) {
        continue
      }

      event.cancel()
      return
    }

    for (block: Block in event.blockList()) {
      if (block.hasInventory() || block.isRich() || block.isNature()) {
        block.drops.clear()
        block.type = Material.AIR
      }
    }
  }
}