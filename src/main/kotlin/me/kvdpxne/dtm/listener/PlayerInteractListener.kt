package me.kvdpxne.dtm.listener

import me.kvdpxne.dtm.command.restricted.wand
import me.kvdpxne.dtm.shared.SelectedPositionStorage
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object PlayerInteractListener : Listener {

  @EventHandler
  fun handleBlockInteract(event: PlayerInteractEvent) {
    if (event.isCancelled) {
      return
    }

    event.item ?: return
    if (!event.item.isSimilar(wand)) {
      return
    }

    event.isCancelled = true

    val player = event.player
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