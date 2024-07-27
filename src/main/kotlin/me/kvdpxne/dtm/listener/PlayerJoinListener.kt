package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.shared.bukkit.equipA
import me.kvdpxne.dtm.shared.bukkit.moveToDefaultSpawnPosition
import me.kvdpxne.dtm.shared.bukkit.reset
import me.kvdpxne.dtm.shared.bukkit.runSynchronousDelayedTask
import me.kvdpxne.dtm.user.OfflineUserService
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener : Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  fun handlePlayerJoin(event: PlayerJoinEvent) {
    //
    val player = event.player

    //
    val identifier = player.uniqueId
    val name = player.name

    //
    val user = OfflineUserService.findUserByIdentifier(identifier)
      ?: OfflineUserService.createUser(identifier, name)

    //
    UserManager.addUser(user)

    // Registers and asynchronously executes after 50 ms
    runSynchronousDelayedTask(1L) {
      player.leaveVehicle()
      player.moveToDefaultSpawnPosition()
    }

    // Resets all available player statistics
    player.reset()

    //
    player.equipA()
  }
}