package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.commands.wand
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.gui.createGameSelectionGui
import me.kvdpxne.dtm.gui.createProfessionSelectionGui
import me.kvdpxne.dtm.gui.createTeamSelectionGui
import me.kvdpxne.dtm.shared.SelectedPositionStorage
import me.kvdpxne.dtm.shared.hardClean
import me.kvdpxne.dtm.user.UserManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

fun Action.isPPM(): Boolean {
  return this == Action.LEFT_CLICK_AIR || this == Action.RIGHT_CLICK_AIR
}

object PlayerInteractListener : Listener {

  @EventHandler
  fun handlePlayerInteract(event: PlayerInteractEvent) {
//    if (event.isCancelled) {
//      return
//    }


    val player = event.player
    val itemInHand = event.item ?: return

    if (itemInHand.isSimilar(JOIN_GAME_ITEM)) {
      val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
      event.isCancelled = true
      createGameSelectionGui(user).open(event.player)
      return
    }

    if (itemInHand.isSimilar(SELECT_TEAM_ITEM)) {
      val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
      val game = GameManager.findByUser(user) ?: return
      event.isCancelled = true
      createTeamSelectionGui(game, user).open(event.player)
      return
    }

    if (itemInHand.isSimilar(SELECT_PROFESSION_ITEM)) {
      val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
      event.isCancelled = true
      createProfessionSelectionGui(user).open(player)
      return
    }

    if (itemInHand.isSimilar(ITEM_GAME_LEAVE)) {
      val user = UserManager.findByIdentifier(event.player.uniqueId) ?: return
      val game = GameManager.findByUser(user) ?: return
      event.isCancelled = true
      game.removeHostage(user)
      player.hardClean()
      player.inventory.setItem(0, JOIN_GAME_ITEM)
      return
    }

    event.item ?: return
    if (!event.item.isSimilar(wand)) {
      return
    }

    event.isCancelled = true

    val block = event.clickedBlock

    // At this stage of the project, the block type of monument must always be
    // obsidian.
    if (Material.OBSIDIAN != block.type) {
      return
    }

    SelectedPositionStorage.selectedBlocks[player.uniqueId] = block.location
    player.sendMessage("Added block located in ${block.location} to temporary storage.")
  }
}