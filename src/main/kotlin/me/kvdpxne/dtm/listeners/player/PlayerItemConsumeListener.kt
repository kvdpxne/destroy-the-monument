package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.shared.player.localUser
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object PlayerItemConsumeListener : Listener {

  @EventHandler
  fun handle(event: PlayerItemConsumeEvent) {
    if (event.isCancelled) {
      return
    }

    val item = event.item
    if (Material.POTION != item.type || 0 != item.durability.toInt()) {
      return
    }

    val player = event.player

    val user = player.localUser ?: return
    user.game ?: return

    player.fireTicks = 0
    player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 3, 0))
  }
}