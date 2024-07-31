package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object PlayerChatListener : Listener {

  private fun formatTeammate(
    teammate: Teammate
  ): String {
    val professionName = teammate.professionQueuingPair.current.displayName
    val teammateName = teammate.user.name

    val teamColor = teammate.team.identity
    val professionColor = teamColor.professionColor
    val teammateColor = teamColor.colorInChat

    return "$professionColor&l$professionName $teammateColor$teammateName&r"
  }

  @EventHandler
  fun handlePlayerChat(event: AsyncPlayerChatEvent) {
    if (event.isCancelled) {
      return
    }

    //
    val player = event.player

    //
    val user = UserManager.findByIdentifier(player.uniqueId) ?: return

    //
    val game = user.game ?: return

    //
    if (!game.isRunning || !game.isStopping) {
      return
    }

    //
    val arena = game.currentArena ?: return

    //
    if (!arena.isLoaded) {
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
    val team = user.team ?: return

    //
    val teammate = user.teammate ?: return

    event.cancel()

    team.sendMessage("${this.formatTeammate(teammate)}&7: &f${event.message}")
  }
}