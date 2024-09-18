package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.Teammate
import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.shared.minecraft.bukkit.localUser
import me.kvdpxne.dtm.shared.minecraft.bukkit.resetExperienceBar
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

/**
 * @since 0.1.0
 */
object PlayerDropItemListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerDropItem(
    event: PlayerDropItemEvent
  ) {
    if (event.isCancelled) {
      return
    }

    // The object of the player who dropped the item
    val player: Player = event.player

    if (player.world.name.equals("lobby", ignoreCase = true)) {
      event.cancel()
      return
    }

    // The user object obtained from the unique identifier of the player object
    val user: LocalUser = player.localUser ?: return

    // The object of the game to which the user is assigned
    val game: LocalGame = user.game ?: return

    //
    if (player.isSneaking) {
      event.cancel()
      createProfessionSelectionGui(user).open(player)
      return
    }

    // If the game has not yet started, then the plugin should not overwrite
    // the event of the player dropping an item
    if (!game.isRunning) {
      return
    }

    // The object of the current arena where the game should or is being played
    val arena = game.currentArena ?: return

    // If the arena is not loaded or the user is not on the arena map, then the
    // plugin should not overwrite the event of the player dropping the item
    if (false == arena.map?.isLoaded || !game.isInArena(user)) {
      return
    }

    // Converted the user object to an object of a teammate who is in a team
    val teammate: Teammate = game.findTeammateByHostage(user) ?: return

    // Cancels further execution of the event
    event.cancel()

    // The ability object of the currently selected profession by a teammate
    val ability = teammate.currentProfession.ability ?: return

    // If the ability is already active, then there should be no way that it
    // can be reactivated until it is used
    if (ability.isActive) {
      return
    }

    // If the ability is marked as possible to activate and is currently ready
    // to use, then it should be marked as active
    if (ability.isActivatable && ability.isReady) {
      ability.markActive()
      ability.whenReady(player)

      // Resets the player's experience bar to somehow signal that the skill
      // has been marked as active
      player.resetExperienceBar()

      // TODO special ability exceptionality
      if ("engineer" == teammate.currentProfession.name) {
        ability.renewDelayed(player, false)
      }
      return
    }
  }
}