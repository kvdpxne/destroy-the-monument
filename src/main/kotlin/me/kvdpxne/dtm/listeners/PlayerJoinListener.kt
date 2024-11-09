package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.shared.player.equipItemsOfGameSelection
import me.kvdpxne.dtm.shared.player.moveToLobby
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.shared.task.runSynchronousDelayedTask
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener : Listener {

  @EventHandler(
    priority = EventPriority.HIGH
  )
  fun handlePlayerJoin(event: PlayerJoinEvent) {
    //
    val player: Player = event.player

    // Registers and asynchronously executes after 50 ms
    runSynchronousDelayedTask(1L) {
      player.leaveVehicle()
      player.moveToLobby()
    }

    // Resets all available player statistics
    player.reset()

    //
    player.equipItemsOfGameSelection()
  }
}