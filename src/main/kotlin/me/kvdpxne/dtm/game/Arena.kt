package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.shared.debug
import org.bukkit.Location
import org.bukkit.World
import java.util.UUID

private val logger: KLogger = KotlinLogging.logger {  }

class Arena(
  val identifier: UUID = UUID.randomUUID(),
  var name: String,
) {

  /**
   * Map of positions for each team where teammates will be spawned after death
   * or being moved to the arena map.
   */
  val spawnPoints: MutableMap<Identity, Location>

  /**
   *
   */
  val monuments: MutableMap<Identity, MutableSet<Monument>>

  var world: World?

  init {
    spawnPoints = linkedMapOf()
    monuments = linkedMapOf()

    world = null
  }

  /**
   *
   */
  fun setSpawnPoint(team: Identity, position: Location) {
    spawnPoints[team] = position
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
      add(Monument(arena = this@Arena, team = team, position = position))
    }.also {
      logger.debug(it) {
        "Assigned a new monument to the $team team in the $this Arena."
      }
    }
  }

//  fun removeMonument(name: Identity, position: Location) {
//    val monumentSet = monuments[name] ?: return
//    monumentSet.remove(position)
//  }
}