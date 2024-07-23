package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.data.DaoUser
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.bukkit.hardClean
import me.kvdpxne.dtm.shared.bukkit.moveToDefaultSpawnPosition
import me.kvdpxne.dtm.shared.bukkit.setItem
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

    var user = DaoUser.findByIdentifier(identifier)
    if (null == user) {
      user = UserManager.createUser(identifier, name)
      DaoUser.insert(user)
    } else {
      UserManager.addUser(user)
    }

    player.hardClean()
    player.moveToDefaultSpawnPosition()

    player.setItem(0, ItemsClipboard.ITEM_GAME_JOIN)
  }
}