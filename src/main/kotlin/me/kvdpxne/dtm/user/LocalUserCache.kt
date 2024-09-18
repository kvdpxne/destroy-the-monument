package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.basics.position.BlockPosition
import me.kvdpxne.dtm.shared.basics.position.EntityPosition

interface LocalUserCache {

  var selectedMonumentPosition: BlockPosition?

  val teleportationHistory: ArrayDeque<EntityPosition>
}