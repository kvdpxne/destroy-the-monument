package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.shared.minecraft.bukkit.equipA
import me.kvdpxne.dtm.shared.minecraft.bukkit.localUser
import me.kvdpxne.dtm.shared.minecraft.bukkit.moveToLobby
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.runSynchronousDelayedTask
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.UserService
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

    val user = UserService.findUserByIdentifier(identifier.toString())
      ?: UserService.createUser(identifier.toString(), name)

    LocalUserManager.addUser(user)

    // Registers and asynchronously executes after 50 ms
    runSynchronousDelayedTask(1L) {
      player.leaveVehicle()
      player.moveToLobby()
    }

    // Resets all available player statistics
    player.reset()

    //
    player.equipA()
  }
}