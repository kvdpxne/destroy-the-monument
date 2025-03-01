package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawRevivalPosition

/**
 * @since 0.1.0
 */
interface RepositoryRevivalPosition {

  /**
   * @since 0.1.0
   */
  suspend fun readRevivalPositions(): Collection<RawRevivalPosition>

  /**
   * @since 0.1.0
   */
  suspend fun readRevivalPositionIdentifiers(): Collection<UUID>

  /**
   * @since 0.1.0
   */
  suspend fun findRevivalPositionByIdentifierOrNull(
    identifier: UUID
  ): RawRevivalPosition?

  /**
   * @since 0.1.0
   */
  suspend fun containsRevivalPositionByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun insertRevivalPosition(
    revivalPosition: RawRevivalPosition?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateRevivalPosition(
    revivalPosition: RawRevivalPosition?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteRevivalPositionByIdentifier(
    identifier: UUID
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun truncateRevivalPositions(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countRevivalPositions(): Long
}