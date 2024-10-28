package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.position.MonumentPosition
import me.kvdpxne.dtm.team.Team

/**
 * @since 0.1.0
 */
interface ArenaMonumentPositionsRepository {

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findArenaMonumentPositionsByArenaIdentifier(
    identifier: UUID
  ): Flow<MonumentPosition<Team>>

  /**
   * @param arena
   * @param monumentPosition
   *
   * @since 0.1.0
   */
  suspend fun insertArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPosition<Team>
  )

  /**
   * @param arena
   * @param monumentPosition
   *
   * @since 0.1.0
   */
  suspend fun deleteArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPosition<Team>
  )

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  suspend fun deleteArenaMonumentPositions(
    arena: Arena
  )
}