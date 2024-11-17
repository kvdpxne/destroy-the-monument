package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.world.WorldsHolder
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

/**
 * Listener to handle [FoodLevelChangeEvent], preventing players from losing
 * hunger in specific areas such as the lobby world or within designated arenas.
 *
 * @since 0.1.0
 */
object PlayerFoodLevelChangeListener : Listener {

  /**
   * Handles the [FoodLevelChangeEvent], canceling the event if a player is in
   * the lobby world or within an arena, preventing hunger depletion in
   * these areas.
   *
   * @param event The [FoodLevelChangeEvent] triggered when a player's food
   *              level changes.
   *
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerFoodLevelChange(
    event: FoodLevelChangeEvent
  ) {
    if (event.isCancelled) {
      return
    }

    val entity: Entity = event.entity
    if (entity !is Player) {
      return
    }

    val world: World = entity.world
    val lobbyWorld: World? = WorldsHolder.lobbyWorld

    if (world == lobbyWorld) {
      event.cancel()
      return
    }

    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)
    if (world != arena?.map?.world) {
      return
    }

    event.cancel()
  }
}