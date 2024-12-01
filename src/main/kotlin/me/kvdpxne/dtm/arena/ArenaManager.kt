package me.kvdpxne.dtm.arena

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import me.kvdpxne.dtm.arena.map.ArenaMap
import me.kvdpxne.dtm.configuration.AdvancedConfiguration
import me.kvdpxne.dtm.shared.WorldUuid

/**
 * Manages arenas in the plugin by maintaining a mapping between arenas
 * and their corresponding world identifiers.
 *
 * This singleton allows adding, removing, and retrieving arenas based on
 * their world identifiers.
 *
 * @since 0.1.0
 */
object ArenaManager {

  /**
   * Lazy delegate for the concurrent map of arenas, initialized with a
   * predefined capacity.
   *
   * @since 0.1.0
   */
  private val arenaByWorldDelegate: Lazy<ConcurrentMap<WorldUuid, Arena>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.ARENA_INITIAL_CAPACITY)
  }

  /**
   * The concurrent map storing arenas indexed by their world identifiers.
   *
   * @since 0.1.0
   */
  private val arenaByWorld: ConcurrentMap<WorldUuid, Arena> by this.arenaByWorldDelegate

  /**
   * Indicates whether the arena manager has been initialized.
   *
   * @return `true` if initialized, `false` otherwise.
   * @since 0.1.0
   */
  private val initialized: Boolean
    get() = this.arenaByWorldDelegate.isInitialized()

  /**
   * Retrieves all currently managed arenas as a collection.
   *
   * @return a collection of arenas or an empty list if not initialized.
   * @since 0.1.0
   */
  val arenas: Collection<Arena>
    get() {
      if (this.initialized) {
        return this.arenaByWorld.values.toList()
      }

      return emptyList()
    }

  /**
   * Retrieves the count of arenas currently managed.
   *
   * @return the number of arenas or `0` if not initialized.
   * @since 0.1.0
   */
  val size: Int
    get() {
      if (this.initialized) {
        return this.arenaByWorld.size
      }

      return 0
    }

  /**
   * Finds an arena by its world identifier.
   *
   * @param identifier the unique world identifier of the arena.
   * @return the corresponding arena, or `null` if not found.
   * @throws IllegalStateException if the arena manager is not initialized.
   * @since 0.1.0
   */
  fun findArenaByWorldIdentifierOrNull(
    identifier: WorldUuid
  ): Arena? {
    if (this.initialized) {
      return this.arenaByWorld[identifier]
    }

    return null
  }

  fun findArenaByWorldIdentifier(
    identifier: WorldUuid
  ): Arena? {
    if (this.initialized) {
      return this.arenaByWorld[identifier]
    }

    error("The arena manager has not yet been initialized.")
  }

  /**
   * Finds an arena by its world identifier or throws an exception if not found.
   *
   * @param identifier the unique world identifier of the arena.
   * @return the corresponding arena.
   * @throws IllegalStateException if the arena manager is not initialized.
   * @throws ArenaNoFoundException if no arena is found for the specified identifier.
   * @since 0.1.0
   * @see findArenaByWorldIdentifierOrNull
   */
  fun findArenaByIdentifier(
    identifier: WorldUuid
  ): Arena {
    return this.findArenaByWorldIdentifierOrNull(identifier)
      ?: throw ArenaNoFoundException(
        """Failed to find the arena assigned to the "$identifier" identifier."""
      )
  }

  /**
   * Adds an arena to the manager.
   *
   * @param arena the arena to add.
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
   * Removes an arena from the manager.
   *
   * @param arena the arena to remove.
   * @since 0.1.0
   */
  fun removeArena(
    arena: Arena
  ) {
    if (!this.initialized) {
      return
    }

    val map: ArenaMap? = arena.map
    if (null != map) {
      this.arenaByWorld.remove(arena.identifier)
    }
  }

  /**
   * Clears all arenas from the manager.
   *
   * @since 0.1.0
   */
  fun removeArenas() {
    if (!this.initialized) {
      return
    }

    this.arenaByWorld.clear()
  }
}