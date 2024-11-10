package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.gui.createGameSelectionGui
import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.gui.createTeamSelectionGui
import me.kvdpxne.dtm.shared.item.ItemsClipboard
import me.kvdpxne.dtm.shared.event.cancel
import me.kvdpxne.dtm.shared.player.equipItemsOfGameSelection
import me.kvdpxne.dtm.shared.block.isMonument
import me.kvdpxne.dtm.shared.item.isNullOrTypeAir
import me.kvdpxne.dtm.shared.event.isRightClick
import me.kvdpxne.dtm.shared.player.equipItemsOfTeamSelection
import me.kvdpxne.dtm.shared.player.localUser
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.shared.world.toBlockPosition
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

/**
 *
 */
object PlayerInteractListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerInteract(
    event: PlayerInteractEvent
  ) {
    val itemInHand: ItemStack = event.item
    if (itemInHand.isNullOrTypeAir()) {
      return
    }

    val player = event.player

    if (event.action.isRightClick()) {

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_GAME_JOIN)) {
        val user = player.localUser
        event.cancel()
        createGameSelectionGui(user).open(player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_TEAM_SELECT)) {
        val user = player.localUser
        val game = user.game

        if (null == game) {
          event.cancel()
          createGameSelectionGui(user).open(player)
          return
        }

        event.cancel()
        createTeamSelectionGui(game, user).open(player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_PROFESSION_SELECT)) {
        val user = player.localUser
        event.cancel()
        createProfessionSelectionGui(user).open(player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_GAME_LEAVE)) {
        val user = player.localUser
        val game = user.game

        if (null == game) {
          event.cancel()
          player.reset()
          player.equipItemsOfGameSelection()
          player.updateInventory()
          return
        }

        event.cancel()
        game.removeHostage(user)
        player.reset()
        player.equipItemsOfGameSelection()
        player.updateInventory()
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_TEAM_LEAVE)) {
        val user = player.localUser
        val game = user.game

        event.cancel()
        game?.findTeamByHostage(user)?.removeTeammate(user)
        player.reset()
        player.equipItemsOfTeamSelection()
        player.updateInventory()
        return
      }
    }

    //
    if (event.isCancelled) {
      return
    }

    //
    if (!itemInHand.isSimilar(ItemsClipboard.ITEM_WAND)) {
      return
    }

    event.cancel()

    val block = event.clickedBlock

    // At this stage of the project, the block type of monument must always be
    // obsidian.
    if (!block.isMonument()) {
      return
    }

    //
    val user = player.localUser ?: return

    //
    user.cache.selectedMonumentPosition = block.location.toBlockPosition()

    //
    player.sendMessage("Added block located in ${block.location} to temporary storage.")
  }
}