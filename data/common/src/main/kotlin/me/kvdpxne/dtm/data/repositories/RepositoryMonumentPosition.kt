package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawMonumentPosition

/**
 * @since 0.1.0
 */
interface RepositoryMonumentPosition {

  /**
   * @since 0.1.0
   */
  suspend fun readMonumentPositions(): Collection<RawMonumentPosition>

  /**
   * @since 0.1.0
   */
  suspend fun readMonumentPositionIdentifiers(): Collection<UUID>

  /**
   * @since 0.1.0
   */
  suspend fun findMonumentPositionByIdentifierOrNull(
    identifier: UUID
  ): RawMonumentPosition?

  /**
   * @since 0.1.0
   */
  suspend fun containsMonumentPositionByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun insertMonumentPosition(
    monumentPosition: RawMonumentPosition?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateMonumentPosition(
    monumentPosition: RawMonumentPosition?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteMonumentPositionByIdentifier(
    identifier: UUID
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun truncateMonumentPositions(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countMonumentPositions(): Long
}