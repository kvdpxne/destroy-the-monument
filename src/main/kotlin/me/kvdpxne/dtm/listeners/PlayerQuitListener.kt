package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.data.DaoUser
import me.kvdpxne.dtm.user.LocalUserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @since 0.1.0
 */
object PlayerQuitListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.MONITOR
  )
  fun handlePlayerQuit(event: PlayerQuitEvent) {
    val player = event.player

    LocalUserManager.findUserByIdentifier(player.uniqueId.toString())?.also {
      DaoUser.updateUser(it)
      LocalUserManager.removeUser(it)
    }
  }
}