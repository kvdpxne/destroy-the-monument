package me.kvdpxne.dtm.game

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
data class RevivalPosition(
  var team: TeamIdentity,
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
    require(TeamService.existsTeamIdentityByIdentifier(this.team.identifier)) {
      "The given team identity cannot be used."
    }
  }

  fun inSpawnRange(
    x: Double,
    y: Double,
    z: Double,
    extended: Double = 3.334
  ): Boolean {
    return this.x in x - extended..x + extended &&
      this.y in y - extended..y + extended &&
      this.z in z - extended..z + extended
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RevivalPosition

    return identifier == other.identifier
  }

  override fun hashCode(): Int {
    return identifier.hashCode()
  }

  override fun toString(): String {
    return "SpawnPoint(team=$team, x=$x, y=$y, z=$z, pitch=$pitch, yaw=$yaw, identifier=$identifier)"
  }
}