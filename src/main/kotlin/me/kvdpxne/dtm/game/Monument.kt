package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import org.bukkit.Location
import java.util.UUID

class Monument(
  val identifier: UUID = UUID.randomUUID(),
  val arena: Arena,
  val team: Identity,
  var position: Location
)