package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.shared.hasInventory
import me.kvdpxne.dtm.shared.isMonument
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

    // The type of block that was placed
    val type = event.block.type

    // If the block type is not a block that has inventory or is a monument,
    // then the block is allowed
    if (type.hasInventory().not() && type.isMonument().not()) {
      return
    }

    // The user who placed the block
    val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return

    // The game to which the user who placed the block belongs
    val game = GameManager.findByUser(user) ?: return

    if (
      game.state.isStarted().not() ||
      null == game.currentArena ||
      game.isInTeam(user).not() ||
      game.isInArenaMap(user).not()
    ) {
      return
    }

    // TODO maybe it should work differently
    // Interrupting the action of placing a block by a player and displaying
    // information about a block that is not allowed is a bit annoying, so it
    // is better to change the type of block being placed to something else.
    event.block.type = Material.COBBLESTONE
  }
}