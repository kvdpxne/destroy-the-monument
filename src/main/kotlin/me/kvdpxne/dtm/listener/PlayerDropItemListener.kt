package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.shared.bukkit.cancel
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

object PlayerDropItemListener : Listener {

  @EventHandler
  fun handlePlayerDropItem(event: PlayerDropItemEvent) {
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
    if (player.isSneaking) {
      event.cancel()
      createProfessionSelectionGui(user).open(player)
      return
    }

    //
    if (!game.isStarted) {
      return
    }

    //
    val arena = game.currentArena ?: return

    //
    if (!arena.isLoaded || !game.isInArenaMap(user)) {
      return
    }

    //
    val team = game.findTeam(user) ?: return

    //
    val teammate = team.findTeammate(user) ?: return

    event.cancel()

    val profession = teammate.currentProfession
    if (profession.name.equals("Archer",true) || profession.name.equals("Scout", true)) {
      return
    }

    profession.ability?.let {
      if (it.isReady) {
        it.whenReady(player)
        it.renewDelayed(player, false)
        return@let
      }
    }
  }
}