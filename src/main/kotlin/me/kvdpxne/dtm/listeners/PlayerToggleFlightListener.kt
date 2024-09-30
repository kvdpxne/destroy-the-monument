package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalTeam
import me.kvdpxne.dtm.shared.minecraft.bukkit.localUser
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleFlightEvent

/**
 * @since 0.1.0
 */
object PlayerToggleFlightListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerToggleFlight(
    event: PlayerToggleFlightEvent
  ) {
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
    val player: Player = event.player

    //
    val user: LocalUser = event.player.localUser ?: return

    //
    val game: LocalGame = user.game ?: return

    if (
      game.isRunning.not() ||
      null == game.currentArena ||
      game.isInTeam(user).not() ||
      game.isInArena(user).not()
    ) {
      return
    }

    //
    val team: LocalTeam = game.findTeamByHostage(user) ?: return

    val teammate = team.getTeammate(user) ?: return

    // Current profession
    val profession = teammate.currentProfession

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