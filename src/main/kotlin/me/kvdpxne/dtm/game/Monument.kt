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

  fun has(x: Int, y: Int, z: Int): Boolean {
    return this.x == x && this.y == y && this.z == z
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Monument

    return identifier == other.identifier
  }

  override fun hashCode(): Int {
    return identifier.hashCode()
  }

  override fun toString(): String {
    return "Monument(team=$team, x=$x, y=$y, z=$z, identifier=$identifier)"
  }
}