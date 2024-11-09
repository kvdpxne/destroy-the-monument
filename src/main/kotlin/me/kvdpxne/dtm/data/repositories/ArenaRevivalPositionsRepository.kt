package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.ArenaUuid
import me.kvdpxne.dtm.team.Team

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
    identifier: ArenaUuid
  ): Flow<RevivalPosition<Team>>

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

  /**
   * @param arena
   * @param revivalPosition
   *
   * @since 0.1.0
   */
  suspend fun deleteArenaRevivalPosition(
    arena: Arena,
    revivalPosition: RevivalPosition<Team>
  )

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  suspend fun deleteArenaRevivalPositions(
    arena: Arena
  )
}