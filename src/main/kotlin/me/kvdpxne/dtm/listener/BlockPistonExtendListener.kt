package me.kvdpxne.dtm.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPistonExtendEvent

object BlockPistonExtendListener : Listener {

  @EventHandler
  fun handleBlockPiston(event: BlockPistonExtendEvent) {
    event.isCancelled = true
  }
}