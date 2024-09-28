package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.game.Team

/**
 * @since 0.1.0
 */
interface RevivalPositionRepository {

  /**
   * @since 0.1.0
   */
  suspend fun findRevivalPositions(): List<RevivalPosition<Team>>

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findRevivalPositionByIdentifier(
    identifier: UUID
  ): RevivalPosition<Team>?

  /**
   * @param revivalPosition
   *
   * @since 0.1.0
   */
  suspend fun insertRevivalPosition(
    revivalPosition: RevivalPosition<Team>
  )

  /**
   * @param revivalPosition
   *
   * @since 0.1.0
   */
  suspend fun updateRevivalPosition(
    revivalPosition: RevivalPosition<Team>
  )

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun deleteRevivalPositionByIdentifier(
    identifier: UUID
  )
}