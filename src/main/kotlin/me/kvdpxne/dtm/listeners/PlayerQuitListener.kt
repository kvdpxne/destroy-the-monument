package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.data.DaoUser
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuitListener : Listener {

  @EventHandler
  fun handlePlayerQuit(event: PlayerQuitEvent) {
    val player = event.player

    UserManager.findByIdentifier(player.uniqueId)?.also {
      DaoUser.updateUser(it)
      UserManager.removeUser(it)
    }
  }
}