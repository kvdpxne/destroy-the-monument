package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

object PlayerFoodLevelChangeListener : Listener {

  @EventHandler
  fun handlePlayerFoodLevelChange(event: FoodLevelChangeEvent) {
    if (event.isCancelled) {
      return
    }

    val player = event.entity

    val user = UserManager.findByIdentifier(player.uniqueId) ?: return
    GameManager.findByUser(user) ?: return

    event.isCancelled = true
  }
}