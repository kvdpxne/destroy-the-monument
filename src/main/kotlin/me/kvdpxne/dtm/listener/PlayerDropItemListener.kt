package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.builtin.KitGui
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

object PlayerDropItemListener : Listener {

  @EventHandler
  fun handlePlayerDropItem(event: PlayerDropItemEvent) {
    if (event.isCancelled) {
      return
    }

    val player = event.player
    if (player.isSneaking.not()) {
      return
    }

    val user = UserManager.findByIdentifier(player.uniqueId) ?: return
    val game = GameManager.findByUser(user) ?: return

    if (game.isInArenaMap(user).not()) {
      return
    }

    event.isCancelled = true
    KitGui().open(player)
  }
}