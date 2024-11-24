package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.map.ArenaMap
import me.kvdpxne.dtm.shared.ArenaUuid

/**
 * @since 0.1.0
 */
interface ArenaRepository {

  /**
   * @throws StackOverflowError
   *
   * @since 0.1.0
   */
  suspend fun findArenas(): List<Arena>

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findArenaByIdentifier(
    identifier: ArenaUuid
  ): Arena?

  /**
   * @param name
   * @param ignoreCase
   *
   * @since 0.1.0
   */
  suspend fun findArenaByName(
    name: String,
    ignoreCase: Boolean = true
  ): Arena?

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  suspend fun insertArena(
    arena: Arena
  ): Int

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  suspend fun updateArena(
    arena: Arena
  ): Int

  suspend fun updateArenaMap(arena: Arena, map: ArenaMap)

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun deleteArenaByIdentifier(
    identifier: ArenaUuid
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun countArenas(): Long
}