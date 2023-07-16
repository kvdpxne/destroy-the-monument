package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.IdentifiableByName
import me.kvdpxne.dtm.shared.Position
import java.util.*

class Arena(val identifier: UUID, var name: String) {

  private val teamSpawnPositionMap: MutableMap<IdentifiableByName, Position>
  private val teamMonumentPositionMap: MutableMap<IdentifiableByName, MutableSet<Position>>

  private var displayName: String?
  private var spawnPosition: Position?

  init {
    teamSpawnPositionMap = linkedMapOf()
    teamMonumentPositionMap = linkedMapOf()

    displayName = null
    spawnPosition = null
  }

  fun addSpawnPosition(name: IdentifiableByName, position: Position) {
    teamSpawnPositionMap[name] = position
  }

  fun addMonument(name: IdentifiableByName, position: Position) {
    val monumentSet = teamMonumentPositionMap[name] ?: hashSetOf()
    monumentSet.add(position)
  }

  fun removeMonument(name: IdentifiableByName, position: Position) {
    val monumentSet = teamMonumentPositionMap[name] ?: return
    monumentSet.remove(position)
  }
}