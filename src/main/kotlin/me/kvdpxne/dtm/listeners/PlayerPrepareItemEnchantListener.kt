package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.PrepareItemEnchantEvent

object PlayerPrepareItemEnchantListener : Listener {

  @EventHandler
  fun handlePlayerPrepareItemEnchant(event: PrepareItemEnchantEvent) {
    if (event.isCancelled) {
      return
    }

    val player = event.enchanter

    val user = UserManager.findByIdentifier(player.uniqueId) ?: return
    val game = GameManager.findByUser(user) ?: return

    if (game.isRunning &&
      null != game.currentArena &&
      game.isInTeam(user) &&
      game.isInArenaMap(user)
    ) {
      event.isCancelled = true
    }
  }
}