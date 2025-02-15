package me.kvdpxne.dtm.arena

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

object BasicArenaManager : ArenaManager {

  /**
   * @since 0.1.0
   */
  private val arenaByWorldIdentifier: Lazy<ConcurrentMap<UUID, Arena>> = lazy {
    ConcurrentHashMap()
  }

  private val arenas: ConcurrentMap<UUID, Arena> by arenaByWorldIdentifier

  override fun getArenas(): Collection<Arena> {
    if (this.isInitialized) {
      return this.arenas.values.toList()
    }
    return emptyList()
  }

  override fun findArenaByWorldIdentifierOrNull(
    identifier: UUID
  ): Arena? {
    check(this.isInitialized) {
      "ArenaManager has not yet been initialized."
    }
    return this.arenas[identifier]
  }

  override fun findArenaByWorldIdentifier(identifier: UUID): Arena {
    return
  }

  override fun addArena(arena: Arena): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeArena(arena: Arena): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeArenas(): Int {
    TODO("Not yet implemented")
  }

  override fun hasArena(arena: Arena): Boolean {
    TODO("Not yet implemented")
  }

  override fun getSize(): Int {
    TODO("Not yet implemented")
  }

  override fun isInitialized(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isEmpty(): Boolean {
    TODO("Not yet implemented")
  }
}