package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.basics.BaseIdentifiableEntityPosition
import me.kvdpxne.dtm.uid.Uid

/**
 * @param team
 * @param x
 * @param y
 * @param z
 * @param pitch
 * @param yaw
 * @param identifier
 */
class RevivalPosition(
  // @formatter:off
  x: Double,
  y: Double,
  z: Double,
  pitch: Float,
  yaw: Float,
  var team: TeamIdentity,
  identifier: String = Uid.next()
  // @formatter:on
) : BaseIdentifiableEntityPosition(x, y, z, pitch, yaw, identifier) {

  companion object {

    /**
     * @since 0.1.0
     */
    const val RADIUS_OF_BLOCK_INTERACTION = 3.874

    /**
     * @since 0.1.0
     */
    const val RADIUS_OF_EXPLOSION_INTERACTION = 11.941
  }

//  init {
//    // Checks if the given team identity can be used to create this object.
//    require(TeamService.existsTeamIdentityByIdentifier(this.team.identifier)) {
//      "The given team identity cannot be used."
//    }
//  }

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