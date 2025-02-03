package me.kvdpxne.dtm.user.cache

import java.util.ArrayDeque
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.EntityPosition

class BasicUserCache : UserCache {

  private var selectedMonumentPosition: BlockPosition? = null
  private var teleportationHistory: ArrayDeque<EntityPosition>? = null

  override fun getSelectedBlockPosition(): BlockPosition? {
    return this.selectedMonumentPosition
  }

  override fun setSelectedBlockPosition(position: BlockPosition) {
    this.selectedMonumentPosition = position
  }

  override fun getTeleportationHistory(): ArrayDeque<EntityPosition>? {
    if (null == this.teleportationHistory) {
      this.teleportationHistory = ArrayDeque()
    }
    return this.teleportationHistory
  }

  override fun clearTeleportationHistory() {
    this.teleportationHistory = null
  }
}