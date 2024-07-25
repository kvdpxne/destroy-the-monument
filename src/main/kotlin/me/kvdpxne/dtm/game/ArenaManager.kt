package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.data.ArenaDao

object ArenaManager {

  /**
   *
   */
  private val _registeredArenas: MutableMap<UUID, Arena> = mutableMapOf()

  val registeredArenas: List<Arena>
    get() = this._registeredArenas.values.toList()

  init {
    ArenaDao.findAll().forEach {
      addArena(it)
    }
  }

  fun count(): Int {
    return _registeredArenas.size
  }

  fun findArenaByIdentifier(identifier: UUID): Arena? {
    return _registeredArenas[identifier]
  }

  /**
   *
   */
  fun findArenaByName(name: String, ignoreCase: Boolean = true): Arena? {
    return _registeredArenas.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

  fun findLoadedArenaByWorldIdentifier(identifier: UUID): Arena? {
    return this._registeredArenas.values.find {
      it.map?.identifier == identifier
    }
  }

  fun isFs(identifier: UUID): Boolean {
    return this._registeredArenas.values.any {
      it.map?.identifier == identifier
    }
  }

  fun addArena(arena: Arena) {
    _registeredArenas[arena.identifier] = arena
  }

  fun removeArena(arena: Arena) {
    _registeredArenas.remove(arena.identifier)
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