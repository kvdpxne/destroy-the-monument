package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import me.kvdpxne.dtm.data.ArenaMonumentsDao
import me.kvdpxne.dtm.data.ArenaSpawnPointsDao
import me.kvdpxne.dtm.data.MonumentDao
import me.kvdpxne.dtm.data.SpawnPointDao
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.shared.debug
import org.bukkit.Location

private val logger: KLogger = KotlinLogging.logger { }

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

  var leftMonuments = 0

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

  fun setSpawnPoint(spawnPoint: SpawnPoint) {
    spawnPoints[spawnPoint.team] = spawnPoint
  }

  /**
   *
   */
  fun setSpawnPoint(team: Identity, position: Location) {
    val spawnPoint = SpawnPoint(team, position.x, position.y, position.z, position.pitch, position.yaw)
    spawnPoints[team] = spawnPoint

    SpawnPointDao.insert(spawnPoint)
    ArenaSpawnPointsDao.insert(this, spawnPoint)
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

  /**
   *
   */
  fun addMonument(team: Identity, position: Location): Boolean {
    val monument = Monument(team, position.blockX, position.blockY, position.blockZ)

    MonumentDao.insert(monument)
    ArenaMonumentsDao.insert(this@Arena, monument)

    return addMonument(monument)
  }

  fun restore() {
    monuments.forEach { (_, value) ->
      value.forEach { monument ->
        monument.restore()
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