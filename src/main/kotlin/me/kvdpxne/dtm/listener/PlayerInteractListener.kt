package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.gui.createGameSelectionGui
import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.gui.createTeamSelectionGui
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.SelectedPositionStorage
import me.kvdpxne.dtm.shared.bukkit.cancel
import me.kvdpxne.dtm.shared.bukkit.isNullOrTypeAir
import me.kvdpxne.dtm.shared.bukkit.isRightClick
import me.kvdpxne.dtm.shared.bukkit.hardClean
import me.kvdpxne.dtm.shared.bukkit.setItem
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

/**
 *
 */
object PlayerInteractListener : Listener {

  /**
   * @since 0.1.0
   */
  @EventHandler
  fun handlePlayerInteract(event: PlayerInteractEvent) {
    val itemInHand = event.item
    if (itemInHand.isNullOrTypeAir()) {
      return
    }

    if (event.action.isRightClick()) {

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_GAME_JOIN)) {
        val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
        event.cancel()
        createGameSelectionGui(user).open(event.player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_TEAM_SELECT)) {
        val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
        val game = user.game ?: return

        event.cancel()
        createTeamSelectionGui(game, user).open(event.player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_PROFESSION_SELECT)) {
        val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
        event.cancel()
        createProfessionSelectionGui(user).open(event.player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_GAME_LEAVE)) {
        val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
        val game = user.game ?: return

        event.cancel()
        game.removeHostage(user)
        event.player.hardClean()
        event.player.setItem(0, ItemsClipboard.ITEM_GAME_JOIN)
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
    if (Material.OBSIDIAN != block.type) {
      return
    }

    val player = event.player
    SelectedPositionStorage.selectedBlocks[player.uniqueId] = block.location
    player.sendMessage("Added block located in ${block.location} to temporary storage.")
  }
}