package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.Team

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
  ): List<MonumentPosition<Team>>

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