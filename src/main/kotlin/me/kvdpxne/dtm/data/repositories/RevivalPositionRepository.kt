package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.position.revival.RevivalPosition
import me.kvdpxne.dtm.shared.RevivalPositionUuid
import me.kvdpxne.dtm.team.Team

/**
 * @since 0.1.0
 */
interface RevivalPositionRepository {

  /**
   * Retrieves a list of all revival positions from the data source.
   * Each revival position is associated with a specific team.
   *
   * @return A list of all `RevivalPosition` entities, each associated with a team.
   *         If no revival positions exist, an empty list is returned.
   *
   * @since 0.1.0
   */
  suspend fun findRevivalPositions(): List<RevivalPosition<Team>>

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findRevivalPositionByIdentifier(
    identifier: RevivalPositionUuid
  ): RevivalPosition<Team>?

  /**
   * @param revivalPosition
   *
   * @since 0.1.0
   */
  suspend fun insertRevivalPosition(
    revivalPosition: RevivalPosition<Team>
  ): Int

  /**
   * @param revivalPosition
   *
   * @since 0.1.0
   */
  suspend fun updateRevivalPosition(
    revivalPosition: RevivalPosition<Team>
  ): Int

  /**
   * Deletes a revival position from the data source, identified by its unique identifier.
   *
   * @param identifier The UUID that uniquely identifies the revival position to be deleted.
   *
   * @since 0.1.0
   */
  suspend fun deleteRevivalPositionByIdentifier(
    identifier: RevivalPositionUuid
  ): Int

  /**
   * Counts the total number of revival positions present in the data source.
   *
   * @return The total count of revival positions.
   *
   * @since 0.1.0
   */
  suspend fun countRevivalPositions(): Long
}