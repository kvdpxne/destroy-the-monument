package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.shared.task.runSynchronousDelayedTask
import me.kvdpxne.dtm.shared.world.toLocation
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

/**
 * @since 0.1.0
 */
object PlayerRespawnListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler(
    priority = EventPriority.HIGHEST
  )
  fun handlePlayerRespawn(
    event: PlayerRespawnEvent
  ) {
    // Obiekt gracza, który się odrodził.
    val player = event.player

    // Obiekt użytkownika pozyskany z unikatowego identyfikatora obiektu
    // gracza, który się odrodził.
    val user: LocalUser = event.player.localUser ?: return

    // Obiekt lokalnej gry, do której jest przypisany obiekt użytkownika.
    val game: LocalGame = user.game ?: return

    //
    if (!game.isRunning) {
      return
    }

    //
    val arena = game.currentArena ?: return

    //
    if (false == arena.map?.isLoaded) {
      return
    }

    //
    val team: LocalTeam = game.findTeamByHostage(user) ?: return

    val spawnPoint = arena.getRevivalPosition(team) ?: return
    val map = arena.map?.world!!
    event.respawnLocation = spawnPoint.toLocation(map)

    //
    val teammate: Teammate = team.getTeammate(user) ?: return

    //
    player.reset()

    //
    if (teammate.hasNextProfession) {
      teammate.shiftProfession()
    }

    //
    val profession: Profession = teammate.currentProfession

    //
    profession.equip(player, team.dyeColor)

    //
    profession.ability?.let {
      //
      it.cancelCooldown()

      //
      it.renewDelayed(player, true)
    }

    //
    runSynchronousDelayedTask(2L) {
      player.noDamageTicks = 20 * GeneralConfiguration.REVIVAL_PLAYER_PROTECTION_DELAY
    }
  }
}