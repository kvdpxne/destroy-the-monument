package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.game.Team

/**
 * @since 0.1.0
 */
interface ArenaRevivalPositionsRepository {

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findArenaRevivalPositionsByArenaIdentifier(
    identifier: UUID
  ): List<RevivalPosition<Team>>

  /**
   * @param arena
   * @param revivalPosition
   *
   * @since 0.1.0
   */
  suspend fun insertArenaRevivalPosition(
    arena: Arena,
    revivalPosition: RevivalPosition<Team>
  )
}