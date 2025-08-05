package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.player.localUserOrNull
import me.kvdpxne.dtm.shared.text.toSingleLines
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.UserService
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Listener to handle [PlayerQuitEvent], ensuring player data is updated and
 * properly removed from any active game upon disconnecting.
 *
 * @since 0.1.0
 */
object PlayerQuitListener : Listener {

  /**
   * Handles the [PlayerQuitEvent], performing necessary cleanup operations
   * when a player leaves the server. Updates user data, removes the player
   * from any active game, and removes them from the user manager.
   *
   * @param event The [PlayerQuitEvent] triggered when a player
   *              leaves the server.
   *
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.MONITOR
  )
  fun handlePlayerQuit(
    event: PlayerQuitEvent
  ) {
    val user: LocalUser = event.player.localUserOrNull ?: return
    UserService.updateUser(user)

    val game: LocalGame? = user.game
    if (null != game) {
      check(game.removeHostage(user)) {
        """
          Unexpected Error: the user when leaving the server belonged to the
          game but was not removed from it even though he should have been
          removed from it.
        """.toSingleLines()
      }
    }

    LocalUserManager.removeUser(user)
  }
}