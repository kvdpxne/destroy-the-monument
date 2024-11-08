package me.kvdpxne.dtm.arena

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * @since 0.1.0
 */
object ArenaManager {

  private val _arenaByWorld: ConcurrentMap<UUID, Arena> =
    ConcurrentHashMap()

  /**
   * @since 0.1.0
   */
  val arenas: Collection<Arena>
    get() = this._arenaByWorld.values.toList()

  /**
   * @since 0.1.0
   */
  val size: Int
    get() = this._arenaByWorld.size

  /**
   * @since 0.1.0
   */
  fun findArenaByWorldIdentifierOrNull(
    identifier: UUID
  ): Arena? {
    return this._arenaByWorld[identifier]
  }

  /**
   * @since 0.1.0
   */
  fun findArenaByIdentifier(
    identifier: UUID
  ): Arena {
    return this.findArenaByWorldIdentifierOrNull(identifier)
      ?: throw ArenaNotFoundException(
        ""
      )
  }

  /**
   * @since 0.1.0
   */
  fun addArena(arena: Arena) {
    val map: ArenaMap? = arena.map
    if (null != map) {
      this._arenaByWorld[map.identifier] = arena
    }
  }

  /**
   * @since 0.1.0
   */
  fun removeArena(arena: Arena) {
    val map: ArenaMap? = arena.map
    if (null != map) {
      this._arenaByWorld.remove(arena.identifier)
    }
  }
}