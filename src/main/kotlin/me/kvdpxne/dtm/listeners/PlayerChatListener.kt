package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalTeam
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.entity.Player
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
    val player: Player = event.player

    //
    val user: User = UserManager.findByIdentifier(player.uniqueId) ?: return

    //
    val game: LocalGame = user.game ?: return

    //
    if (!game.isRunning || !game.isStopping) {
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
      for (hostage: User in game.hostages) {
        if (null != hostage.team || null != hostage.teammate) {
          continue
        }

        event.format = "&f%s&7: &f%s"
      }
      return
    }

    //
    val team: LocalTeam = user.team ?: return

    //
    val teammate = user.teammate ?: return

    event.cancel()

    team.sendMessage("${this.formatTeammate(teammate)}&7: &f${event.message}")
  }
}