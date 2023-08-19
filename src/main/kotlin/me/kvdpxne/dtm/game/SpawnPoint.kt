package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.shared.Identity

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

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as SpawnPoint

    return identifier == other.identifier
  }

  override fun hashCode(): Int {
    return identifier.hashCode()
  }

  override fun toString(): String {
    return "SpawnPoint(team=$team, x=$x, y=$y, z=$z, pitch=$pitch, yaw=$yaw, identifier=$identifier)"
  }
}