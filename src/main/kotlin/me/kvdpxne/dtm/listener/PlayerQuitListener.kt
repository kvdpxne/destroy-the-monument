package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.data.UserDao
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

object PlayerQuitListener : Listener {

  @EventHandler
  fun handlePlayerQuit(event: PlayerQuitEvent) {
    val player = event.player

    UserManager.findByIdentifier(player.uniqueId).also {
      if (null == it) {
        return@also
      }

      UserDao.update(it)
      UserManager.removeUser(it)
    }
  }
}