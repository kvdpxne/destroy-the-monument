package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.toLocation
import me.kvdpxne.dtm.shared.fillExperienceBar
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Bukkit
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

    //
    val teammate = team.findTeammate(user) ?: return

    //
    teammate.professionQueuingPair.run {
      if (this.hasNext()) {
        this.shift()
      }

      this.current.equip(player, teammate.team.identity.dyeColor)

      this.current.ability?.let {
        if (it.readyAfterDeath) {
          it.markReady()
          it.whenReady(player)

          // Fill player exp bar after 200 ms
          Bukkit.getScheduler().runTaskLaterAsynchronously(
            DestroyTheMonument.instance,
            { player.fillExperienceBar() },
            4L
          )
          return
        }

        it.run(player, true)
      }
    }

    Bukkit.getScheduler().runTaskLater(
      DestroyTheMonument.instance,
      { player.noDamageTicks = 2 * 20 },
      2L
    )
  }
}