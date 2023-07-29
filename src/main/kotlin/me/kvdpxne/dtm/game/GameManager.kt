package me.kvdpxne.dtm.game

import java.util.*

object GameManager {

  private val identifierGameMap: MutableMap<UUID, Game>

  init {
    identifierGameMap = linkedMapOf()

    // TODO Delete in the future.
    UUID.fromString("4d966ed3-86b8-4acd-bb48-aed322cf14fe").let {
      identifierGameMap[it] = Game(it, "test")
    }
  }

  fun findGameByIdentifier(identifier: UUID): Game? {
    return identifierGameMap[identifier]
  }

  fun findGameByName(name: String): Game? {
    return identifierGameMap.values.find { it.name.equals(name, true) }
  }

  fun findGameByArenaName(name: String, ignoreCase: Boolean = true): Game? {
    return identifierGameMap.values
      .find { it.arena?.name.equals(name, ignoreCase) }
  }
}