package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.shared.basics.position.BlockPosition
import me.kvdpxne.dtm.uid.Uid

/**
 * @param name
 * @param identifier
 *
 * @since 0.1.0
 */
class Arena(
  // @formatter:off
  val name      : String,
      identifier: String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier) {

  /**
   * Map of positions for each team where teammates will be spawned after death
   * or being moved to the arena map.
   *
   * @since 0.1.0
   */
  val _revivalPositions: MutableMap<String, RevivalPosition> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  val _monumentPositions: MutableMap<String, MutableSet<MonumentPosition>> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  var map: ArenaMap? = null

  /**
   * @since 0.1.0
   */
  val revivalPositions: List<RevivalPosition>
    get() = this._revivalPositions.values.toList()

  /**
   * @since 0.1.0
   */
  val monumentPositions: List<MonumentPosition>
    get() = this._monumentPositions.values.flatten().toList()

  /**
   * @since 0.1.0
   */
  val isLoaded: Boolean
    get() = null != this.map?.world

  /**
   * @since 0.1.0
   */
  fun findRevivalPosition(
    team: TeamIdentity
  ): RevivalPosition? {
    return this._revivalPositions[team.identifier]
  }

  fun findPositionMonument(
    identifier: String
  ): Set<MonumentPosition>? {
    return this._monumentPositions[identifier]
  }

  /**
   * @since 0.1.0
   */
  fun findMonument(
    blockPosition: BlockPosition
  ): MonumentPosition? {
    return this.monumentPositions.find {
      it.isIn(blockPosition)
    }
  }

  /**
   * @since 0.1.0
   */
  fun findMonument(
    x: Int,
    y: Int,
    z: Int
  ): MonumentPosition? {
    for (monuments: MutableSet<MonumentPosition> in this._monumentPositions.values) {
      for (monument: MonumentPosition in monuments) {
        if (monument.isIn(x, y, z)) {
          return monument
        }
      }
    }
    return null
  }

  fun addPositionMonument(
    position: MonumentPosition
  ) {
    val fs = this._monumentPositions[position.team.identifier] ?: mutableSetOf()
    fs.add(position)
    this._monumentPositions[position.team.identifier] = fs
  }

  fun addRevivalPosition(
    position: RevivalPosition
  ) {
    this._revivalPositions[position.team.identifier] = position
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    if (!super.equals(other)) return false

    other as Arena

    return map == other.map
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + map.hashCode()
    return result
  }

  override fun toString(): String {
    return "Arena(identifier=$identifier, name='$name', map=$map)"
  }
}