package me.kvdpxne.dtm.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause

object EntityDamageListener : Listener {

  @EventHandler
  fun handleEntityDamage(event: EntityDamageEvent) {
    if (event.isCancelled) {
      return
    }

    if (event.entity !is Player) {
      return
    }

    val player = event.entity as Player
    if (DamageCause.VOID == event.cause) {
      event.isCancelled = true
      player.setHealth(0.0)
    }
  }
}