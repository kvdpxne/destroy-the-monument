package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.shared.text.toSingleLines
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.UserService
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent

/**
 * Listener to handle player kick events, ensuring user data is updated and
 * properly removed from any active game upon being kicked from the server.
 *
 * @since 0.1.0
 */
object PlayerKickListener : Listener {

  /**
   * Handles the [PlayerKickEvent], performing necessary cleanup operations
   * when a player is kicked from the server. Updates user data, removes the
   * player from any active game, and removes them from the user manager.
   *
   * @param event The [PlayerKickEvent] triggered when a player is kicked.
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.MONITOR
  )
  fun handlePlayerKick(
    event: PlayerKickEvent
  ) {
    if (event.isCancelled) {
      return
    }

    val user: LocalUser = event.player.localUser
    UserService.updateUser(user)

    val game: LocalGame? = user.game
    if (null != game) {
      check(game.removeHostage(user)) {
        """
          Unexpected Error: the user when kicked off the server belonged to the
          game but was not removed from it even though he should have been
          removed from it.
        """.toSingleLines()
      }
    }

    LocalUserManager.removeUser(user)
  }
}