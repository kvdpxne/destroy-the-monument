package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.toLocation
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object PlayerRespawnListener : Listener {

  @EventHandler
  fun handlePlayerRespawn(event: PlayerRespawnEvent) {
    val player = event.player

    val user = UserManager.findByIdentifier(player.uniqueId) ?: return
    val game = GameManager.findByUser(user) ?: return
    val team = game.findTeam(user) ?: return

    val arena = game.currentArena
    if (null != arena) {
      val spawnPoint = arena.spawnPoints[team.identity] ?: return
      val map = arena.map?.world!!
      event.respawnLocation = spawnPoint.toLocation(map)
    }

    val teammate = team.findTeammate(user) ?: return

    if (null != teammate.nextProfession) {
      teammate.profession = teammate.nextProfession
      teammate.nextProfession = null
    }

    teammate.profession?.equip(player, (teammate.teamColor as DefaultTeamColor).dyeColor)
  }
}