package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.EntityPosition

interface LocalUserCache {

  var selectedMonumentPosition: BlockPosition?

  val teleportationHistory: ArrayDeque<EntityPosition>
}