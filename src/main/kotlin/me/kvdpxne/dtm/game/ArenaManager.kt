package me.kvdpxne.dtm.game

import java.util.UUID

object ArenaManager {

  /**
   *
   */
  private val arenas: MutableMap<UUID, Arena> = mutableMapOf()

  fun count(): Int {
    return arenas.size
  }

  fun getArenas(): Collection<Arena> {
    val capacity = count()
    if (0 >= capacity) {
      return emptySet()
    }
    return buildSet(capacity) {
      addAll(arenas.values)
    }
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


    addArena(arena)
    return arena
  }
}