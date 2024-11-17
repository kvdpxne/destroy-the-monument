package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

/**
 * @since 0.1.0
 */
object PlayerDropItemListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerDropItem(
    event: PlayerDropItemEvent
  ) {
    if (event.isCancelled) {
      return
    }

    // The object of the player who dropped the item
    val player: Player = event.player

    if (player.world.name.equals("lobby", ignoreCase = true)) {
      event.cancel()
      return
    }

    // The user object obtained from the unique identifier of the player object
    val user: LocalUser = player.localUser ?: return

    // The object of the game to which the user is assigned
    val game: LocalGame = user.game ?: return

    // If the game has not yet started, then the plugin should not overwrite
    // the event of the player dropping an item
    if (!game.isRunning) {
      return
    }

    // The object of the current arena where the game should or is being played
    val arena = game.currentArena ?: return

    // If the arena is not loaded or the user is not on the arena map, then the
    // plugin should not overwrite the event of the player dropping the item
    if (false == arena.map?.isLoaded || !game.isInArena(user)) {
      return
    }

    // Cancels further execution of the event
    event.cancel()
  }
}