package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.UserService
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
    event.player.localUser.let { localUser: LocalUser ->
      UserService.updateUser(localUser)
      LocalUserManager.removeUser(localUser)
    }
  }
}