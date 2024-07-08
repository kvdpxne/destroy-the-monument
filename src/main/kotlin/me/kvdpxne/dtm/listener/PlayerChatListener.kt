package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Teammate
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
    val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return

    //
    val game = GameManager.findByUser(user) ?: return

    if (
      !game.state.isStarted() ||
      null == game.currentArena ||
      !game.isInTeam(user) ||
      !game.isInArenaMap(user)
    ) {
      return
    }

    val team = game.findTeam(user)!!
    val teammate = team.findTeammate(user)!!

    event.isCancelled = true

    team.sendMessage("${this.formatTeammate(teammate)}&7: &f${event.message}")
  }
}