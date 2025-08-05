package me.kvdpxne.dtm.user.cache

import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.EntityPosition

class LocalUserCacheImpl : LocalUserCache {

  override var selectedMonumentPosition: BlockPosition? = null
  override val teleportationHistory: ArrayDeque<EntityPosition> = ArrayDeque()
}