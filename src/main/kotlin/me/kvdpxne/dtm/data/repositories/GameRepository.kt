package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.team.Team

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
    identifier: GameUuid
  ): Game<Team>?

  /**
   * @param name
   * @param ignoreCase
   *
   * @since 0.1.0
   */
  suspend fun findGameByName(
    name: String,
    ignoreCase: Boolean = true
  ): Game<Team>?

  /**
   * @param game
   *
   * @since 0.1.0
   */
  suspend fun insertGame(
    game: Game<Team>
  ): Int

  /**
   * @param game
   *
   * @since 0.1.0
   */
  suspend fun updateGame(
    game: Game<Team>
  ): Int

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun deleteGameByIdentifier(
    identifier: GameUuid
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun countGames(): Long
}