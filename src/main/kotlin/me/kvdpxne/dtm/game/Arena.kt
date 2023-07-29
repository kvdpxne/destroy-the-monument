package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.IdentifiableByName
import org.bukkit.Location
import org.bukkit.World
import java.util.*

class Arena(
  val identifier: UUID,
  var name: String,
  var worldName: String? = null
) {

  private val teamSpawnPositionMap: MutableMap<IdentifiableByName, Location>
  private val teamMonumentPositionMap: MutableMap<IdentifiableByName, MutableSet<Location>>

  var world: World?

  init {
    teamSpawnPositionMap = linkedMapOf()
    teamMonumentPositionMap = linkedMapOf()

    world = null
  }

  fun addSpawnPosition(name: IdentifiableByName, position: Location) {
    teamSpawnPositionMap[name] = position
  }

  fun addMonument(name: IdentifiableByName, position: Location) {
    val monumentSet = teamMonumentPositionMap[name] ?: hashSetOf()
    monumentSet.add(position)
  }

  fun removeMonument(name: IdentifiableByName, position: Location) {
    val monumentSet = teamMonumentPositionMap[name] ?: return
    monumentSet.remove(position)
  }
}