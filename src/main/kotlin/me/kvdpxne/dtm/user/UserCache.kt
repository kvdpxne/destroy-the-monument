package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.basics.position.BlockPosition
import me.kvdpxne.dtm.shared.basics.position.EntityPosition

class UserCache {

  var selectedMonumentPosition: BlockPosition? = null
  val teleportationHistory: ArrayDeque<EntityPosition> = ArrayDeque()
}