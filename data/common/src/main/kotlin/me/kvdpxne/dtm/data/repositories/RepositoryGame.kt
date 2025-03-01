package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawGame

/**
 * @since 0.1.0
 */
interface RepositoryGame {

  /**
   * @since 0.1.0
   */
  suspend fun readGames(): Collection<RawGame>

  /**
   * @since 0.1.0
   */
  suspend fun readGameIdentifiers(): Collection<String>

  /**
   * @since 0.1.0
   */
  suspend fun findGameByIdentifierOrNull(
    identifier: UUID
  ): RawGame?

  /**
   * @since 0.1.0
   */
  suspend fun findGameByNameOrNull(
    name: String
  ): RawGame?

  /**
   * @since 0.1.0
   */
  suspend fun insertGame(
    game: RawGame
  )

  /**
   * @since 0.1.0
   */
  suspend fun updateGame(
    game: RawGame
  )

  /**
   * @since 0.1.0
   */
  suspend fun deleteGameByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun countGames(): Long
}