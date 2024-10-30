package me.kvdpxne.dtm.listeners

import java.util.logging.Logger
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.configuration.Configuration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object PlayerChatListener : Listener {

  private fun formatTeammate(
    teammate: Teammate
  ): String {
    val professionName = teammate.currentProfession.displayName
    val teammateName = teammate.user.name

    val teamColor = teammate.team
    val professionColor = teamColor.professionColor
    val teammateColor = teamColor.colorInChat

    return "$professionColor&l$professionName $teammateColor$teammateName&r"
  }

  @EventHandler
  fun handlePlayerChat(
    event: AsyncPlayerChatEvent
  ) {
    if (event.isCancelled) {
      return
    }

    //
    val user: LocalUser = event.player.localUser

    //
    val game: LocalGame = user.game ?: return

    //
    if (!game.isStarting && !game.isRunning) {
      return
    }

    //
    val arena = game.currentArena ?: return

    //
    if (false == arena.map?.isLoaded) {
      return
    }

    //
    if (!game.isInArena(user)) {
      for (hostage: LocalUser in game.hostages) {
        if (null != hostage.team || null != hostage.teammate) {
          continue
        }

        event.format = "&f%s&7: &f%s"
      }
      return
    }

    //
    val team: Pair<LocalTeam, Teammate> = game.findTeammateTeamByHostage(user)
      ?: return

    //
    event.cancel()

    //
    val message: String = event.message

    //
    team.first.sendMessage("${this.formatTeammate(team.second)}&7: &f$message")

    if (Configuration.TRACE_MESSAGES_IN_GAME) {
      val logger: Logger = DestroyTheMonument.instance?.logger ?: return

      val professionName: String = team.second.currentProfession.displayName
      val teammateName: String = team.second.user.name

      logger.info("[${game.name}] [${team.first.name}] $professionName $teammateName: $message")
    }
  }
}