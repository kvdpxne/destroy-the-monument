package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.shared.player.localUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

object PlayerFoodLevelChangeListener : Listener {

  @EventHandler
  fun handlePlayerFoodLevelChange(
    event: FoodLevelChangeEvent
  ) {
    if (event.isCancelled) {
      return
    }

    val user = (event.entity as Player).localUser ?: return
    user.game ?: return

    event.isCancelled = true
  }
}