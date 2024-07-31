package me.kvdpxne.dtm.listeners

import me.kvdpxne.dtm.gui.createGameSelectionGui
import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.gui.createTeamSelectionGui
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.minecraft.bukkit.cancel
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipA
import me.kvdpxne.dtm.shared.minecraft.bukkit.isMonument
import me.kvdpxne.dtm.shared.minecraft.bukkit.isNullOrTypeAir
import me.kvdpxne.dtm.shared.minecraft.bukkit.isRightClick
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.toBlockPosition
import me.kvdpxne.dtm.user.UserManager
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

    val player = event.player

    if (event.action.isRightClick()) {

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_GAME_JOIN)) {
        val user = UserManager.findByIdentifier(player.uniqueId) ?: return
        event.cancel()
        createGameSelectionGui(user).open(player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_TEAM_SELECT)) {
        val user = UserManager.findByIdentifier(player.uniqueId) ?: return
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
        val user = UserManager.findByIdentifier(player.uniqueId) ?: return
        event.cancel()
        createProfessionSelectionGui(user).open(player)
        return
      }

      if (itemInHand.isSimilar(ItemsClipboard.ITEM_GAME_LEAVE)) {
        val user = UserManager.findByIdentifier(player.uniqueId) ?: return
        val game = user.game

        if (null == game) {
          event.cancel()
          player.reset()
          player.equipA()
          player.updateInventory()
          return
        }

        event.cancel()
        game.removeHostage(user)
        player.reset()
        player.equipA()
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
    val user = UserManager.findByIdentifier(player.uniqueId) ?: return

    //
    user.cache.selectedMonumentPosition = block.location.toBlockPosition()

    //
    player.sendMessage("Added block located in ${block.location} to temporary storage.")
  }
}