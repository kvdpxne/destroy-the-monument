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
    println("1")
    val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return

    // Tries to find a user in any game.
    println("2")
    val game = GameManager.findByUser(user) ?: return

    // Tries to find the user's team in a previously found game.
    println("3")
    val team = game.findTeam(user) ?: return

    //
    println("4")
    val arena = game.currentArena ?: return

    //
    println("5")
    val monument = arena.findMonument(event.block.location) ?: return

    //
    println("6")
    if (monument.destroyed) {
      return
    }

    println("7")
    if (team.identity == monument.team) {
      event.isCancelled = true
      user.performer.sendMessage("You cannot destroy your team's monument")
      return
    }

    monument.destroy(arena)

    game.hostages.values.forEach {
      it.performer.run {
        sendMessage("An $it player has destroyed an ${monument.team} team monument.")
        sendMessage("There are ${arena.leftMonuments} monuments left.")
      }
    }

    if (0 >= arena.leftMonuments) {
      game.stop()
    }
  }
}