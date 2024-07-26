package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.game.toLocation
import me.kvdpxne.dtm.shared.bukkit.fillExperienceBar
import me.kvdpxne.dtm.shared.bukkit.runSynchronousDelayedTask
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object PlayerRespawnListener : Listener {

  @EventHandler
  fun handlePlayerRespawn(event: PlayerRespawnEvent) {
    val player = event.player

    //
    val user = UserManager.findByIdentifier(player.uniqueId) ?: return

    //
    val game = user.game ?: return

    //
    if (!game.isStarted) {
      return
    }

    //
    val arena = game.currentArena ?: return

    //
    if (!arena.isLoaded) {
      return
    }

    //
    val team = game.findTeam(user) ?: return

    val spawnPoint = arena._spawnPoints[team.identity] ?: return
    val map = arena.map?.world!!
    event.respawnLocation = spawnPoint.toLocation(map)

    //
    val teammate = team.findTeammate(user) ?: return

    //
    teammate.professionQueuingPair.run {
      if (this.hasNext()) {
        this.shift()
      }

      this.current.equip(player, teammate.team.identity.dyeColor)

      this.current.ability?.let {
        //
        it.cancelCooldown()

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

        it.renewDelayed(player, true)
      }
    }

    //
    runSynchronousDelayedTask(2L) {
      player.noDamageTicks = 20 * 2
    }
  }
}