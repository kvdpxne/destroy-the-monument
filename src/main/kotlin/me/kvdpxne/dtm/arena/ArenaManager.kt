package me.kvdpxne.dtm.arena

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import me.kvdpxne.dtm.configuration.AdvancedConfiguration
import me.kvdpxne.dtm.shared.WorldUuid

/**
 * @since 0.1.0
 */
object ArenaManager {

  /**
   * @since 0.1.0
   */
  private val arenaByWorldDelegate: Lazy<ConcurrentMap<WorldUuid, Arena>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.ARENA_INITIAL_CAPACITY)
  }

  /**
   * @since 0.1.0
   */
  private val arenaByWorld: ConcurrentMap<WorldUuid, Arena> by this.arenaByWorldDelegate

  /**
   * @since 0.1.0
   */
  val arenas: Collection<Arena>
    get() {
      if (this.arenaByWorldDelegate.isInitialized()) {
        return this.arenaByWorld.values.toList()
      }
      return emptyList()
    }

  /**
   * @since 0.1.0
   */
  val size: Int
    get() {
      if (this.arenaByWorldDelegate.isInitialized()) {
        return this.arenaByWorld.size
      }

      return 0
    }

  /**
   * @since 0.1.0
   */
  fun findArenaByWorldIdentifierOrNull(
    identifier: WorldUuid
  ): Arena? {
    if (this.arenaByWorldDelegate.isInitialized()) {
      return this.arenaByWorld[identifier]
    }

    return null
  }

  /**
   * @since 0.1.0
   */
  fun findArenaByIdentifier(
    identifier: WorldUuid
  ): Arena {
    return this.findArenaByWorldIdentifierOrNull(identifier)
      ?: throw ArenaNotFoundException(
        ""
      )
  }

  /**
   * @since 0.1.0
   */
  fun addArena(
    arena: Arena
  ) {
    val map: ArenaMap? = arena.map
    if (null != map) {
      this.arenaByWorld[map.identifier] = arena
    }
  }

  /**
   * @since 0.1.0
   */
  fun removeArena(
    arena: Arena
  ) {
    if (!this.arenaByWorldDelegate.isInitialized()) {
      return
    }

    val map: ArenaMap? = arena.map
    if (null != map) {
      this.arenaByWorld.remove(arena.identifier)
    }
  }
}