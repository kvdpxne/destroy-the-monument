package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team

/**
 * @since 0.1.0
 */
interface GameRepository {

  /**
   * @since 0.1.0
   */
  suspend fun findGames(): List<Game<Team>>

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findGameByIdentifier(
    identifier: UUID
  ): Game<Team>?

  /**
   * @param name
   * @param ignoreCase
   *
   * @since 0.1.0
   */
  suspend fun findGameByName(
    name: String,
    ignoreCase: Boolean
  ): Game<Team>?

  /**
   * @param game
   *
   * @since 0.1.0
   */
  suspend fun insertGame(
    game: Game<Team>
  )

  /**
   * @since 0.1.0
   */
  suspend fun countGames(): Long
}