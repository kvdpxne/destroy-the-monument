package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.player.equipItemsOfGameSelection
import me.kvdpxne.dtm.shared.player.moveToLobby
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.shared.task.runAsynchronousDelayedTask
import me.kvdpxne.dtm.shared.text.toSingleLines
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * Listener for handling [PlayerJoinEvent].
 *
 * @since 0.1.0
 */
object PlayerJoinListener : Listener {

  /**
   * Handles player join event and initializes the player state.
   *
   * @param event The player join event.
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.HIGH
  )
  fun handlePlayerJoin(
    event: PlayerJoinEvent
  ) {
    // A player who just joined the server.
    val player: Player = event.player

    runAsynchronousDelayedTask(1L) {
      /* The condition will be met if the player is a passenger in any vehicle
       * and is properly removed from it. In addition, (if the plugin is
       * running in debug mode) a message will be printed that the specified
       * player was removed from the vehicle when joining the server.
       *
       * Explanation: A player cannot be moved to another position if he is
       * a passenger in any vehicle.
       **/
      if (player.leaveVehicle()) {
        Debug.log {
          """
            The ${player.name} player was in a vehicle as a passenger when
            joining the server and was forcibly removed from the vehicle to
            ensure that this player could be moved to the primary spawn on the
            lobby world.
          """.toSingleLines()
        }
      }

      // Attempt to move the player to the primary position on the lobby world
      // or if the lobby world is not defined then to the primary position on
      // the first world in the collection of currently loaded worlds.
      player.moveToLobby()
    }

    // Resets all available player attributes and abilities
    // that can negatively affect gameplay.
    player.reset()

    // Equips the player with items to interact with the game during
    // the initial lobby phase.
    player.equipItemsOfGameSelection()
  }
}