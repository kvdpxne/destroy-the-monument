package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.ArenaDao
import java.util.UUID

object ArenaManager {

  /**
   *
   */
  val arenas: MutableMap<UUID, Arena> = mutableMapOf()

  init {
    ArenaDao.findAll().forEach {
      addArena(it)
    }
  }

  fun count(): Int {
    return arenas.size
  }

  fun findArenaByIdentifier(identifier: UUID): Arena? {
    return arenas[identifier]
  }

  /**
   *
   */
  fun findArenaByName(name: String, ignoreCase: Boolean = true): Arena? {
    return arenas.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

  fun addArena(arena: Arena) {
    arenas[arena.identifier] = arena
  }

  fun removeArena(arena: Arena) {
    arenas.remove(arena.identifier)
  }

  fun createArena(name: String): Arena? {
    require(name.isNotBlank()) {
      "Arena name cannot be empty."
    }

    require(4 <= name.length) {
      "Arena name cannot be shorter than 4 characters."
    }

    if (null != findArenaByName(name)) {
      return null
    }

    val identifier = UUID.randomUUID()
    val arena = Arena(identifier, name)

    ArenaDao.insert(arena)
    addArena(arena)
    return arena
  }
}