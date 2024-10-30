package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.player.localUser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.PrepareItemEnchantEvent

object PlayerPrepareItemEnchantListener : Listener {

  @EventHandler
  fun handlePlayerPrepareItemEnchant(event: PrepareItemEnchantEvent) {
    if (event.isCancelled) {
      return
    }

    val user = event.enchanter.localUser ?: return
    val game: LocalGame = user.game ?: return

    if (game.isRunning &&
      null != game.currentArena &&
      game.isInTeam(user) &&
      game.isInArena(user)
    ) {
      event.isCancelled = true
    }
  }
}