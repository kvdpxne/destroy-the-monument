package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import me.kvdpxne.dtm.shared.debug

private val logger: KLogger = KotlinLogging.logger { }

class Arena(
  val identifier: UUID = UUID.randomUUID(),
  var name: String
) {

  /**
   * Map of positions for each team where teammates will be spawned after death
   * or being moved to the arena map.
   */
  val _spawnPoints: MutableMap<TeamIdentity, RevivalPosition> = mutableMapOf()

  /**
   *
   */
  val monuments: MutableMap<TeamIdentity, MutableSet<Monument>> = mutableMapOf()

  val revivalPositions: List<RevivalPosition>
    get() = this._spawnPoints.values.toList()

  /**
   *
   */
  var map: ArenaMap? = null

  val isLoaded: Boolean
    get() = null != this.map

  fun findMonument(x: Int, y: Int, z: Int): Monument? {
    for (monumentList in monuments.values) {
      for (monument in monumentList) {
        if (monument.isIn(x, y, z)) {
          return monument
        }
      }
    }
    return null
  }

  fun setSpawnPoint(spawnPoint: RevivalPosition) {
    _spawnPoints[spawnPoint.team] = spawnPoint
  }

  fun addMonument(monument: Monument): Boolean {
    val team = monument.team
    return monuments.getOrPut(team) {
      // Creates new instances of the modified set, if one is not assigned to
      // the given team.
      mutableSetOf()
    }.run {
      add(monument)
    }.also {
      logger.debug(it) {
        "Assigned a new monument to the $team team in the $this Arena."
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Arena

    if (identifier != other.identifier) return false
    if (name != other.name) return false
    if (map != other.map) return false

    return true
  }

  override fun hashCode(): Int {
    var result = identifier.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + (map?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "Arena(identifier=$identifier, name='$name', map=$map)"
  }


//  fun removeMonument(name: Identity, position: Location) {
//    val monumentSet = monuments[name] ?: return
//    monumentSet.remove(position)
//  }


}