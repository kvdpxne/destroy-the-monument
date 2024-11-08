package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.shared.event.cancel
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByBlockEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause

/**
 * @since 0.1.0
 */
object EntityDamageByBlockListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handleEntityDamageByBlock(
    event: EntityDamageByBlockEvent
  ) {
    if (event.isCancelled) {
      return
    }

    // If an entity receives damage from a void or, e.g. lava, the block
    // that deals this damage to the entity will always be NULL.
    if (null != event.damager) {
      return
    }

    if (DamageCause.VOID != event.cause) {
      return
    }

    val entity: Entity = event.entity
    if (entity !is Player) {
      return
    }

    val world: World = entity.world
    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)

    if (world != arena?.map?.world) {
      return
    }

    event.cancel()
    entity.setHealth(0.0)
  }
}