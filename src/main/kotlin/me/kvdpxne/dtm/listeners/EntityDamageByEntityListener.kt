package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.damage.DamageManager
import me.kvdpxne.dtm.damage.DamageOwner
import me.kvdpxne.dtm.shared.reflection.PrimitiveTypes
import me.kvdpxne.dtm.shared.reflection.Reflection
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

/**
 * @since 0.1.0
 */
object EntityDamageByEntityListener : Listener {

  /**
   * @since 0.1.0
   */
  fun getDamage(
    event: EntityDamageEvent
  ): Double {
    return Reflection
      .getMethod(EntityDamageEvent::class.java, "getDamage", PrimitiveTypes.DOUBLE)
      .invoke(event) as Double
  }

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handleEntityDamage(
    event: EntityDamageByEntityEvent
  ) {
    if (event.isCancelled) {
      return
    }

    val attacker: Entity = event.damager
    if (attacker !is Player) {
      return
    }

    val victim: Entity = event.entity
    if (victim !is Player) {
      return
    }

    if (victim == attacker) {
      return
    }

    val world: World = victim.world
    if (world != attacker.world) {
      return
    }

    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)
    if (world != arena?.map?.world) {
      return
    }

    val damageOwner: DamageOwner = DamageManager.findFs(victim.uniqueId)
    damageOwner.addDamage(attacker.uniqueId, this.getDamage(event))
  }
}