package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.implementations.bukkit.BukkitPositionConverter
import me.kvdpxne.dtm.positionConverter
import me.kvdpxne.dtm.shared.PositionConvertException
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin

class PlayerRespawnListener(val plugin: JavaPlugin) : Listener {

  @EventHandler
  fun handlePlayerRespawn(event: PlayerRespawnEvent) {
    val player = event.player

    val user = UserManager.getUserByIdentifierOrNull(player.uniqueId) ?: return
    val game = GameManager.findByUser(user) ?: return
    val team = game.findTeam(user) ?: return

    val arena = game.currentArena
    if (null != arena) {
      val spawnPoint = arena.spawnPoints[team.identity] ?: return
      val map = arena.map?.world!!

      val converter = positionConverter as BukkitPositionConverter

      val location = try {
        converter.fromPosition(map, spawnPoint.position)
      } catch (exception: PositionConvertException) {
        return
      }

      event.respawnLocation = location
    }

    val teammate = team.findTeammate(user) ?: return

    if (null != teammate.nextProfession) {
      teammate.profession = teammate.nextProfession
      teammate.nextProfession = null
    }

    teammate.profession?.equip(player, (teammate.teamColor as DefaultTeamColor).dyeColor)
    Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, {
      teammate.profession?.addEffect(player)
    }, 20L)
  }
}