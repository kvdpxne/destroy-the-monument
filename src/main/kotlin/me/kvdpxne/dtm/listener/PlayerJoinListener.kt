package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.implementations.bukkit.BukkitInGameUser
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserLifecycleService
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener : Listener {

  @EventHandler
  fun handlePlayerJoin(event: PlayerJoinEvent) {
    val player = event.player

    //
    val userIdentifier = player.uniqueId
    val userName = player.name

    /*
     * First, it searches for the user in the cache by the given user identifier
     * Secondly, if the user was not found in the cache, the user is searched
     * in the database according to the given user identifier.
     */
    var user: User? = UserManager.getUserByIdentifierOrNull(
      identifier = userIdentifier,
      load = true
    )

    if (null == user) {
      user = User(
        identifier = userIdentifier,
        name = userName
      )

      UserLifecycleService.createUser(user)
      UserManager.addUser(user)
    }

    user.inGame = BukkitInGameUser(user)
  }
}