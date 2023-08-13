package me.kvdpxne.dtm.listener

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

    if (team.identity == monument.team) {
      event.isCancelled = true
      user.performer.sendMessage("You cannot destroy your team's monument")
      return
    }

    game.hostages.values.forEach {
      it.performer.run {
        sendMessage("An $it player has destroyed an ${monument.team} team monument.")
        sendMessage("There are ${arena.monuments[monument.team]?.size} monuments left.")
      }
    }

    monument.destroy()
  }
}