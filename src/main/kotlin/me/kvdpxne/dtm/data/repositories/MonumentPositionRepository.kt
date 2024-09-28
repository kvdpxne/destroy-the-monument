package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.Team

/**
 * @since 0.1.0
 */
interface MonumentPositionRepository {

  /**
   * @since 0.1.0
   */
  suspend fun findMonumentPositions(): List<MonumentPosition<Team>>

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findMonumentPositionByIdentifier(
    identifier: UUID
  ): MonumentPosition<Team>?

  /**
   * @param monumentPosition
   *
   * @since 0.1.0
   */
  suspend fun insertMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  )

  /**
   * @param monumentPosition
   *
   * @since 0.1.0
   */
  suspend fun updateMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  )

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun deleteMonumentPositionByIdentifier(
    identifier: UUID
  )
}