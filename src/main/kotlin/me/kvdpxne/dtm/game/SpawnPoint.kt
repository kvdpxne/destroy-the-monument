package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import java.util.UUID

/**
 * @param team
 * @param x
 * @param y
 * @param z
 * @param pitch
 * @param yaw
 * @param identifier
 */
data class SpawnPoint(
  var team: Identity,
  var x: Double,
  var y: Double,
  var z: Double,
  var pitch: Float,
  var yaw: Float,
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