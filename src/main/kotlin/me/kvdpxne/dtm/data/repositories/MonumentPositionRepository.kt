package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.Team

/**
 * A repository interface for managing monument positions in a game.
 *
 * Provides methods to create, retrieve, update, and delete [MonumentPosition]
 * entities.
 * Each [MonumentPosition] is associated with a specific [Team].
 *
 * @since 0.1.0
 */
interface MonumentPositionRepository {

  /**
   * Retrieves a list of all monument positions from the data source.
   * Each [MonumentPosition] is associated with a specific team in the game.
   *
   * @return A list of all monument positions, each linked to a team.
   *         The result is an empty list if no positions are found.
   *
   * @since 0.1.0
   */
  suspend fun findMonumentPositions(): List<MonumentPosition<Team>>

  /**
   * Retrieves a specific monument position by its unique identifier.
   *
   * @param identifier The [UUID] that uniquely identifies the monument
   *                   position.
   *
   * @return The monument position associated with the given identifier, or null
   *         if not found.
   *
   * @since 0.1.0
   */
  suspend fun findMonumentPositionByIdentifier(
    identifier: UUID
  ): MonumentPosition<Team>?

  /**
   * Inserts a new monument position into the data source.
   *
   * @param monumentPosition The [MonumentPosition] object representing the
   *                         monument position to be inserted.
   *
   * @since 0.1.0
   */
  suspend fun insertMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  ): Int

  /**
   * Updates an existing monument position in the data source.
   *
   * @param monumentPosition The [MonumentPosition] object with updated
   *                         information to be persisted.
   *
   * @since 0.1.0
   */
  suspend fun updateMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  ): Int

  /**
   * Deletes a monument position identified by its unique identifier.
   *
   * @param identifier The [UUID] of the monument position to be deleted.
   *
   * @since 0.1.0
   */
  suspend fun deleteMonumentPositionByIdentifier(
    identifier: UUID
  ): Int

  /**
   * Counts the total number of monument positions available in the data source.
   *
   * @return The total number of monument positions.
   *
   * @since 0.1.0
   */
  suspend fun countMonumentPositions(): Long
}