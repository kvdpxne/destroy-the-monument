package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.material.hasInventory
import me.kvdpxne.dtm.shared.material.isMonument
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

/**
 * @since 0.1.0
 */
object BlockPlaceListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.HIGH
  )
  fun handleBlockPlace(
    event: BlockPlaceEvent
  ) {
    if (event.isCancelled) {
      return
    }

    if (event.canBuild().not()) {
      return
    }

    // Obiekt użytkownika uzyskany na podstawie unikatowego identyfikatora
    // gracza, który postawił jakiś blok
    val user: LocalUser = event.player.localUser ?: return

    // The game to which the user who placed the block belongs
    val game: LocalGame = user.game ?: return

    // The game should have a started state, and the user should be on a team
    if (!game.isRunning || !game.isInTeam(user)) {
      return
    }

    // The current game arena should not be undefined
    val arena = game.currentArena ?: return

    // The user should be on the map of the current arena
    if (!game.isInArena(user)) {
      return
    }

    //
    val location = event.block.location

    if (84 < location.y) {
      event.cancel()
      user.sendConfiguredMessage { configuration: Configuration ->
        configuration.BUILD_HEIGHT_LIMIT_MESSAGE
      }
      return
    }

    for (revivalPosition: RevivalPosition<*> in arena.revivalPositions) {
      if (revivalPosition.isNear(
          location.x,
          location.y,
          location.z,
          Configuration.RADIUS_OF_BLOCK_INTERACTION
        )
      ) {
        event.cancel()
        user.sendConfiguredMessage { configuration: Configuration ->
          configuration.SPAWN_BLOCK_PLACEMENT_DENIED_MESSAGE
        }
        return
      }
    }

    // The type of block that was placed
    val type = event.block.type

    // If the block type is not a block that has inventory or is a monument,
    // then the block is allowed
    if (!type.hasInventory() && !type.isMonument()) {
      return
    }

    // TODO maybe it should work differently
    // Interrupting the action of placing a block by a player and displaying
    // information about a block that is not allowed is a bit annoying, so it
    // is better to change the type of block being placed to something else.
    event.block.type = Material.COBBLESTONE
  }
}