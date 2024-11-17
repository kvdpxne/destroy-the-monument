package me.kvdpxne.dtm.listeners.player

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.ArenaManager
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.shared.event.cancel
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.PrepareItemEnchantEvent

/**
 * @since 0.1.0
 */
object PlayerPrepareItemEnchantListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerPrepareItemEnchant(
    event: PrepareItemEnchantEvent
  ) {
    if (event.isCancelled) {
      return
    }

    //
    if (GeneralConfiguration.BLOCK_ENCHANTING) {
      return
    }

    // Światu, na którym znajduje się gracz, który próbuje zakląć item.
    val world: World = event.enchanter.world

    // Arena otrzymana z unikatowego identyfikatora świata.
    val arena: Arena? = ArenaManager.findArenaByWorldIdentifierOrNull(world.uid)

    // Jeżeli świat, na którym jest gracz to taki sam świat jak świat areny,
    // gracz jest w trakcie gry na tym świecie.
    if (world != arena?.map?.world) {
      return
    }

    //
    event.cancel()
  }
}