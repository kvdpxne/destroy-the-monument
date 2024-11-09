package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.gui.GuiHolder
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.world.WorldsHolder
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

/**
 * @since 0.1.0
 */
object PlayerInventoryClickListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerInventoryClick(
    event: InventoryClickEvent
  ) {
    if (event.isCancelled) {
      return
    }

    if (InventoryType.CHEST == event.inventory.type) {
      val holder: InventoryHolder = event.inventory.holder
      if (holder !is GuiHolder) {
        return
      }

      event.cancel()
      holder.handleAction(event)
      return
    }

    val world: World = event.whoClicked.world
    val lobbyWorld: World? = WorldsHolder.lobbyWorld

    if (world == lobbyWorld) {
      if (GameMode.CREATIVE == event.whoClicked.gameMode) {
        return
      }

      event.cancel()
      return
    }

    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)
    if (world != arena?.map?.world) {
      return
    }

    for (game: LocalGame in GameManager.findGameByArena(arena.identifier)) {
      if (!game.isRunning) {
        event.cancel()
      }
    }
  }
}