package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleFlightEvent

object PlayerToggleFlightListener : Listener {

  @EventHandler
  fun handlePlayerToggleFlight(event: PlayerToggleFlightEvent) {
    if (event.isCancelled) {
      return
    }

    //
    val mode = event.player.gameMode

    //
    if (mode != GameMode.SURVIVAL && mode != GameMode.ADVENTURE) {
      return
    }

    //
    val player = event.player

    //
    val user = UserManager.findByIdentifier(player.uniqueId) ?: return

    //
    val game = GameManager.findByUser(user) ?: return

    if (
      game.isRunning.not() ||
      null == game.currentArena ||
      game.isInTeam(user).not() ||
      game.isInArena(user).not()
    ) {
      return
    }

    //
    val team = game.findTeam(user) ?: return

    val teammate = team.findTeammate(user) ?: return

    // Current profession
    val profession = teammate.professionQueuingPair.current

    if ("scout" != profession.name) {
      return
    }

    val ability = profession.ability ?: return

    if (!ability.isReady) {
      return
    }

    //
    event.isCancelled = true

    //
    player.isFlying = false
    player.allowFlight = false

    //
    player.velocity = player.location.direction.multiply(0.995F).setY(1)
    player.fallDistance = 0.0F

    ability.renewDelayed(player)
  }
}