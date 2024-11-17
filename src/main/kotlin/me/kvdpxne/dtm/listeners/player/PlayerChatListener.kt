package me.kvdpxne.dtm.listeners.player

import java.util.logging.Logger
import me.kvdpxne.dtm.DestroyTheMonument
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @since 0.1.0
 */
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

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerChat(
    event: AsyncPlayerChatEvent
  ) {
    if (event.isCancelled) {
      return
    }

    // The user registered in the local memory retrieved from the player
    // who typed something in the chat.
    val user: LocalUser = event.player.localUser

    //
    val game: LocalGame = user.game ?: return

    //
    if (game.isInitialized || game.isStarting) {
      event.cancel()
      game.sendMessage("&7[&6G&7] ${user.name}: &6${event.message}")
      return
    }

    //
    if (!game.isInArena(user)) {
      for (hostage: LocalUser in game.hostages) {
        if (null != hostage.team) {
          continue
        }

        event.cancel()
        hostage.sendMessage("&7[&6G&7] ${user.name}: &6${event.message}")
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
    team.first.sendMessage("${formatTeammate(team.second)}&7: &f$message")

    if (GeneralConfiguration.TRACE_MESSAGES_IN_GAME) {
      val logger: Logger = DestroyTheMonument.instance.logger ?: return

      val professionName: String = team.second.currentProfession.displayName
      val teammateName: String = team.second.user.name

      logger.info("[${game.name}] [${team.first.name}] $professionName $teammateName: $message")
    }
  }
}