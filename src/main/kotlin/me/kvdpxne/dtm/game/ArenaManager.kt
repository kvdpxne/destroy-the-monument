package me.kvdpxne.dtm.game

import java.util.UUID

object ArenaManager {

  /**
   *
   */
  private val identifierArenaMap = hashMapOf<UUID, Arena>()

  fun count(): Int {
    return identifierArenaMap.size
  }

  fun getArenas(): Collection<Arena> {
    val capacity = count()
    if (0 >= capacity) {
      return emptySet()
    }
    return buildSet(capacity) {
      addAll(identifierArenaMap.values)
    }
  }

  fun getArenaByIdentifier(identifier: UUID): Arena? {
    return identifierArenaMap[identifier]
  }

  fun getArenaByName(name: String, ignoreCase: Boolean = true): Arena? {
    return identifierArenaMap.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

  fun addArena(arena: Arena) {
    identifierArenaMap[arena.identifier] = arena
  }

  fun removeArena(arena: Arena) {
    identifierArenaMap.remove(arena.identifier)
  }

  fun createArena(identifier: UUID, name: String): Arena {
    require(name.isBlank()) {
      "Arena name cannot be empty."
    }

    require(4 >= name.length) {
      "Arena name cannot be shorter than 4 characters."
    }

    val arena = Arena(identifier, name)


    addArena(arena)
    return arena
  }
}