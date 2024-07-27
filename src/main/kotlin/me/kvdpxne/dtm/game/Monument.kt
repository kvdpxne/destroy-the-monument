package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.basics.BaseIdentifiableBlockPosition
import me.kvdpxne.dtm.uid.Uid

/**
 * @param x
 * @param y
 * @param z
 * @param team
 * @param identifier
 *
 * @since 0.1.0
 */
class Monument(
  // @formatter:off
      x         : Int,
      y         : Int,
      z         : Int,
  val team      : TeamIdentity,
      identifier: String = Uid.next()
  // @formatter:on
) : BaseIdentifiableBlockPosition(x, y, z, identifier) {

//  init {
//    // Checks if the given team identity can be used to create this object.
//    require(TeamService.existsTeamIdentityByIdentifier(this.team.identifier)) {
//      "The given team identity cannot be used."
//    }
//  }

  /**
   * @since 0.1.0
   */
  var isDestroyed: Boolean = false
    private set

  /**
   * @since 0.1.0
   */
  fun markDestroyed() {
    this.isDestroyed = true
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    if (!super.equals(other)) return false

    other as Monument

    return team == other.team
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + team.hashCode()
    return result
  }
}