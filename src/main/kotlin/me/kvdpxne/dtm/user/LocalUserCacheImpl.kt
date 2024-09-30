package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.basics.position.BlockPosition
import me.kvdpxne.dtm.shared.basics.position.EntityPosition

class LocalUserCacheImpl : LocalUserCache {

  override var selectedMonumentPosition: BlockPosition? = null
  override val teleportationHistory: ArrayDeque<EntityPosition> = ArrayDeque()
}