package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.data.DaoUser
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent

/**
 * @since 0.1.0
 */
object PlayerKickListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.MONITOR
  )
  fun handlePlayerKick(event: PlayerKickEvent) {
    if (event.isCancelled) {
      return
    }

    UserManager.findByIdentifier(event.player.uniqueId)?.let {
      DaoUser.updateUser(it)
      UserManager.removeUser(it)
    }
  }
}