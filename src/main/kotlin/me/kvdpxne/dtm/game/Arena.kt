package me.kvdpxne.dtm.game

import java.util.UUID

class Arena(
  val identifier: UUID,
  var world: UUID,
  var name: String,
  var displayName: String,
  var monuments: List<Monument>,
  val respawnPoints: Array<RespawnPoint>
)