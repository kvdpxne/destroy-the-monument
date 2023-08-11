package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object BlockBreakListener : Listener {

  @EventHandler
  fun handleBlockBreak(event: BlockBreakEvent) {
    if (event.isCancelled) {
      return
    }

    val player = event.player

    val user = UserManager.findByIdentifier(player.uniqueId) ?: return
    val game = GameManager.findGameWithUser(user) ?: return

    //
    if (game.isInTeam(user).not()) {
      return
    }

    //
    val arena = game.currentArena ?: return
    val location = event.block.location

    val monument = arena.findMonument(location.blockX, location.blockY, location.blockZ) ?: return
    game.hostages.values.forEach {
      it.performer.run {
        sendMessage("An $it player has destroyed an ${monument.team} team monument.")
        sendMessage("There are ${arena.monuments[monument.team]?.size} monuments left.")
      }
    }
  }
}