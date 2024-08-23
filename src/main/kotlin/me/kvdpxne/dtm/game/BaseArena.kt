package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid

/**
 * @param name
 * @param identifier
 *
 * @since 0.1.0
 */
class BaseArena(
  // @formatter:off
  override val name      : String,
               identifier: String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier), Arena {

  /**
   * Map of positions for each team where teammates will be spawned after death
   * or being moved to the arena map.
   *
   * @since 0.1.0
   */
  val _revivalPositions: MutableMap<String, RevivalPosition<out Team>> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  val _monumentPositions: MutableMap<String, MutableSet<MonumentPosition<out Team>>> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  override var map: ArenaMap? = null

  /**
   * @since 0.1.0
   */
  override val revivalPositions: List<RevivalPosition<out Team>>
    get() = this._revivalPositions.values.toList()

  /**
   * @since 0.1.0
   */
  override val monumentPositions: List<MonumentPosition<out Team>>
    get() = this._monumentPositions.values.flatten().toList()

  /**
   * @since 0.1.0
   */
  val isLoaded: Boolean
    get() = null != this.map?.world

  /**
   * @since 0.1.0
   */
  override fun getRevivalPosition(
    team: Team
  ): RevivalPosition<out Team>? {
    return this._revivalPositions[team.identifier]
  }

  override fun getMonumentPositions(
    team: Team
  ): List<MonumentPosition<out Team>> {
    return this._monumentPositions[team.identifier]?.toList() ?: emptyList()
  }

  fun findPositionMonument(
    identifier: String
  ): Set<MonumentPosition<out Team>>? {
    return this._monumentPositions[identifier]
  }

  /**
   * @since 0.1.0
   */
  override fun getMonumentPosition(
    x: Int,
    y: Int,
    z: Int
  ): MonumentPosition<out Team>? {
    // NOTE:
    //
    for (monuments: Set<MonumentPosition<*>> in this._monumentPositions.values) {
      for (monument: MonumentPosition<*> in monuments) {
        if (monument.isIn(x, y, z)) {
          return monument
        }
      }
    }
    return null
  }

  fun addPositionMonument(
    position: MonumentPosition<out Team>
  ) {
    val fs = this._monumentPositions[position.team.identifier] ?: mutableSetOf()
    fs.add(position)
    this._monumentPositions[position.team.identifier] = fs
  }

  fun addRevivalPosition(
    position: RevivalPosition<out Team>
  ) {
    this._revivalPositions[position.team.identifier] = position
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    if (!super.equals(other)) return false

    other as BaseArena

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