package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawGame

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface GameRepository {

  /**
   * @since 0.1.0
   */
  suspend fun readGames(): Collection<RawGame>

  /**
   * @since 0.1.0
   */
  suspend fun readGameIdentifiers(): Collection<UUID>


  suspend fun readGameNames(): Collection<String>

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
    game: RawGame?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateGame(
    game: RawGame?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteGameByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun deleteGame(
    game: RawGame
  ): Boolean {
    return this.deleteGameByIdentifier(game.identifier)
  }

  /**
   * @since 0.1.0
   */
  suspend fun countGames(): Long
}