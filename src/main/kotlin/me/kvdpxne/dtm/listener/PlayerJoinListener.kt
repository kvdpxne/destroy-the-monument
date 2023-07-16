package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.data.UserDao
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener : Listener {

  @EventHandler
  fun handlePlayerJoin(event: PlayerJoinEvent) {
    val player = event.player

    val identifier = player.uniqueId
    val name = player.name

    var user = UserDao.getUserByIdentifier(identifier)
    if (null == user) {
      user = UserManager.createUser(identifier, name)
      UserDao.insert(user)
    } else {
      UserManager.addUser(user)
    }
  }
}