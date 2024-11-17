package me.kvdpxne.dtm.listeners.entity

import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.block.hasInventory
import me.kvdpxne.dtm.shared.block.isPlant
import me.kvdpxne.dtm.shared.block.isRich
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

/**
 * @since 0.1.0
 */
object EntityExplodeListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.HIGH
  )
  fun handleEntityExplode(
    event: EntityExplodeEvent
  ) {
    if (event.isCancelled) {
      return
    }

    // The object of location an entity explosion
    val location: Location = event.location

    for (game: Game<*> in GameManager.games) {
      if (game !is LocalGame) {
        continue
      }

      if (!game.isRunning && !game.isStopping) {
        continue
      }

      //
      val arena: Arena = game.currentArena
        ?: throw IllegalStateException("No arena found for $game")

      //
      val world: World = arena.map?.world
        ?: throw IllegalStateException("")

      //
      if (world != location.world) {
        return
      }

      for (revivalPosition: RevivalPosition<*> in arena.revivalPositions) {
        if (!revivalPosition.isNear(
            location.x,
            location.y,
            location.z,
            GeneralConfiguration.RADIUS_OF_EXPLOSION_INTERACTION
          )
        ) {
          continue
        }

        event.cancel()
        return
      }

      for (block: Block in event.blockList()) {
        if (!block.hasInventory() && !block.isRich() && !block.isPlant()) {
          continue
        }

        block.drops.clear()
        block.type = Material.AIR
      }
    }
  }
}