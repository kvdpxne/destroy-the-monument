package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaMap

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
    identifier: UUID
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
  )

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  suspend fun updateArena(
    arena: Arena
  )

  suspend fun updateArenaMap(arena: Arena, map: ArenaMap)

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun deleteArenaByIdentifier(
    identifier: UUID
  )

  /**
   * @since 0.1.0
   */
  suspend fun countArenas(): Long
}