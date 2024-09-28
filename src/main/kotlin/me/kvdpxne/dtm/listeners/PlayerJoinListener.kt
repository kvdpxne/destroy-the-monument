package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.listeners.netty.TeammateActiveProfessionAbilityListener
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipA
import me.kvdpxne.dtm.shared.minecraft.bukkit.moveToLobby
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.runSynchronousDelayedTask
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserBuilder
import me.kvdpxne.dtm.user.UserService
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerJoinListener : Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  fun handlePlayerJoin(event: PlayerJoinEvent) {
    //
    val player: Player = event.player

    //
    var user: User? = UserService.findUserByIdentifier(event.player.uniqueId)

    if (null == user) {
      user = UserBuilder.create(player).build()
      UserService.createUser(user)
    }

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

    TeammateActiveProfessionAbilityListener.addPlayer(player)
  }
}