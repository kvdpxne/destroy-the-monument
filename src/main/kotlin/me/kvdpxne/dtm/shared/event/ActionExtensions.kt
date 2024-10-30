package me.kvdpxne.dtm.shared.event

import org.bukkit.event.block.Action

/**
 * @since 0.1.0
 */
fun Action.isLeftClick(): Boolean {
  return Action.LEFT_CLICK_AIR == this || Action.LEFT_CLICK_BLOCK == this
}

/**
 * @since 0.1.0
 */
fun Action.isRightClick(): Boolean {
  return Action.RIGHT_CLICK_AIR == this || Action.RIGHT_CLICK_BLOCK == this
}