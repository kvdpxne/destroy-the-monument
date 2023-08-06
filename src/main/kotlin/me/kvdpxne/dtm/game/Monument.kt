package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import java.util.UUID

data class Monument(
  var team: Identity,
  var x: Int,
  var y: Int,
  var z: Int,
  // An automatically generated unique object identifier.
  val identifier: UUID = UUID.randomUUID()
) {

  init {
    // Checks if the given team identity can be used to create this object.
    check(null != DefaultTeamColor.findByIdentity(this.team)) {
      "The given team identity cannot be used."
    }
  }
}