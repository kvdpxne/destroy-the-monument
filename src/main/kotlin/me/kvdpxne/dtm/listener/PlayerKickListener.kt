package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.data.UserDao
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent

object PlayerKickListener : Listener {

  @EventHandler
  fun handlePlayerKick(event: PlayerKickEvent) {
    if (event.isCancelled) {
      return
    }

    UserManager.findByIdentifier(event.player.uniqueId)?.let {
      UserDao.update(it)
      UserManager.removeUser(it)
    }
  }
}