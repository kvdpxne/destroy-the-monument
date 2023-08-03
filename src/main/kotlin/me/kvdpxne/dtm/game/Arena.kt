package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.Identity
import org.bukkit.Location
import org.bukkit.World
import java.util.*

class Arena(
  val identifier: UUID,
  var name: String,
  var worldName: String? = null
) {

  private val teamSpawnPositionMap: MutableMap<Identity, Location>
  private val teamMonumentPositionMap: MutableMap<Identity, MutableSet<Location>>

  var world: World?

  init {
    teamSpawnPositionMap = linkedMapOf()
    teamMonumentPositionMap = linkedMapOf()

    world = null
  }

  fun addSpawnPosition(name: Identity, position: Location) {
    teamSpawnPositionMap[name] = position
  }

  fun addMonument(name: Identity, position: Location) {
    val monumentSet = teamMonumentPositionMap[name] ?: hashSetOf()
    monumentSet.add(position)
  }

  fun removeMonument(name: Identity, position: Location) {
    val monumentSet = teamMonumentPositionMap[name] ?: return
    monumentSet.remove(position)
  }
}