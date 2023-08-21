package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.shared.EntityPosition
import me.kvdpxne.dtm.shared.Identity

/**
 * @param team
 * @param position
 * @param identifier
 */
data class SpawnPoint(
  var team: Identity,
  val position: EntityPosition,
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
    return "SpawnPoint(team=$team, position=$position, identifier=$identifier)"
  }
}