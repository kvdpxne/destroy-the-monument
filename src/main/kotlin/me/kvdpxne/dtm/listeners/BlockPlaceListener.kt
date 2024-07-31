package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.shared.basics.isNear
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.shared.minecraft.bukkit.hasInventory
import me.kvdpxne.dtm.shared.minecraft.bukkit.isMonument
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object BlockPlaceListener : Listener {

  @EventHandler
  fun handleBlockPlace(event: BlockPlaceEvent) {
    if (event.isCancelled) {
      return
    }

    if (event.canBuild().not()) {
      return
    }

    // The user who placed the block
    val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return

    // The game to which the user who placed the block belongs
    val game = GameManager.findByUser(user) ?: return

    // The game should have a started state, and the user should be on a team
    if (!game.isRunning || !game.isInTeam(user)) {
      return
    }

    // The current game arena should not be undefined
    val arena = game.currentArena ?: return

    // The user should be on the map of the current arena
    if (!game.isInArena(user)) {
      return
    }

    //
    val location = event.block.location

    if (location.y > 84) {
      event.cancel()
      user.sendMessage("&6&lDTM &7> &cOsiągnełeś możliwy limit budowania na tej mapie.")
      return
    }

    //
    for (revivalPosition: RevivalPosition in arena.revivalPositions) {
      if (revivalPosition.isNear(location, RevivalPosition.RADIUS_OF_BLOCK_INTERACTION)) {
        event.cancel()
        user.sendMessage("&6&lDTM &7> &cNie możesz stawiać bloków na spawnie.")
        return
      }
    }

    // The type of block that was placed
    val type = event.block.type

    // If the block type is not a block that has inventory or is a monument,
    // then the block is allowed
    if (!type.hasInventory() && !type.isMonument()) {
      return
    }

    // TODO maybe it should work differently
    // Interrupting the action of placing a block by a player and displaying
    // information about a block that is not allowed is a bit annoying, so it
    // is better to change the type of block being placed to something else.
    event.block.type = Material.COBBLESTONE
  }
}