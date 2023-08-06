package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.shared.debug
import org.bukkit.Location
import java.util.UUID

private val logger: KLogger = KotlinLogging.logger {  }

class Arena(
  val identifier: UUID = UUID.randomUUID(),
  var name: String
) {

  /**
   * Map of positions for each team where teammates will be spawned after death
   * or being moved to the arena map.
   */
  val spawnPoints: MutableMap<Identity, SpawnPoint> = mutableMapOf()

  /**
   *
   */
  val monuments: MutableMap<Identity, MutableSet<Monument>> = mutableMapOf()

  /**
   *
   */
  var map: ArenaMap? = null

  /**
   *
   */
  fun setSpawnPoint(team: Identity, position: Location) {
    spawnPoints[team] = SpawnPoint(
      team,
      position.x,
      position.y,
      position.z,
      position.pitch,
      position.yaw
    )
  }

  /**
   *
   */
  fun addMonument(team: Identity, position: Location): Boolean {
    return monuments.getOrPut(team) {
      // Creates new instances of the modified set, if one is not assigned to
      // the given team.
      mutableSetOf()
    }.run {
      add(Monument(team, position.blockX, position.blockY, position.blockZ))
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