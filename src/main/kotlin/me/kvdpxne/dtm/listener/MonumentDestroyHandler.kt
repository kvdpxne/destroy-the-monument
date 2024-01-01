package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.findMonument
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object MonumentDestroyHandler : Listener {

  @EventHandler
  fun handleBlockBreak(event: BlockBreakEvent) {
    if (event.isCancelled) {
      return
    }

    //
    val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return

    // Tries to find a user in any game.
    val game = GameManager.findByUser(user) ?: return

    // Tries to find the user's team in a previously found game.
    val team = game.findTeam(user) ?: return

    //
    val arena = game.currentArena ?: return

    //
    val monument = arena.findMonument(event.block.location) ?: return

    //
    if (monument.destroyed) {
      return
    }

    val teamIdentity = team.identity
    val monumentIdentity = monument.team

    if (teamIdentity == monumentIdentity) {
      event.isCancelled = true
      user.sendMessage("&7You cannot destroy your team's monument!")
      return
    }

    monument.destroy(arena)

    game.sendMessages {
      (teamIdentity as DefaultTeamColor)
      val coloredUser = teamIdentity.chatColor.toString() + user.name

      (monumentIdentity as DefaultTeamColor)
      val coloredMonument = monumentIdentity.chatColor.toString() + monumentIdentity.key

      arrayOf(
        "&7An $coloredUser &7player has destroyed the $coloredMonument &7team monument.",
        "&7There are &6${arena.leftMonuments} &7monuments left."
      )
    }

    if (0 >= arena.leftMonuments) {
      game.stop()
    }
  }
}