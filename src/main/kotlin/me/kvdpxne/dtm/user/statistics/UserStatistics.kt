package me.kvdpxne.dtm.user.statistics

import me.kvdpxne.dtm.data.state.MutableState
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.StatisticsUuid
import me.kvdpxne.dtm.statistics.Statistics

/**
 * Represents user-specific statistical data for gameplay, including
 * metrics such as games played, games won, and games lost.
 *
 * This interface extends [Statistics] to inherit player performance metrics
 * (like kills and assists), and implements [Identifiable] and [MutableState]
 * to allow unique identification and mutable state handling.
 *
 * @since 0.1.0
 */
interface UserStatistics : Identifiable<StatisticsUuid>, Statistics, MutableState {

  /**
   * The number of games the user has played.
   *
   * This count reflects the total gameplay experience and engagement level
   * of the user.
   *
   * @since 0.1.0
   */
  val playedGames: Int

  /**
   * The number of games the user has won.
   *
   * Tracks successful game outcomes, contributing to overall performance
   * metrics.
   *
   * @since 0.1.0
   */
  val gamesWon: Int

  /**
   * The number of games the user has lost.
   *
   * Reflects unsuccessful game outcomes and contributes to user statistics.
   *
   * @since 0.1.0
   */
  val gamesLost: Int

  /**
   * Increments the number of played games by the specified amount (defaults
   * to 1).
   *
   * This method uses the protected `add` helper function from
   * `AbstractStatistics` to ensure the game count doesn't go below zero.
   *
   * @param playedGames The number of played games to add (defaults to 1).
   *
   * @since 0.1.0
   */
  fun addPlayedGames(playedGames: Int = 1)

  /**
   * Increments the number of games won by 1 (or a specified value).
   *
   * @param gamesWon The number of games won to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addGamesWon(gamesWon: Int = 1)

  /**
   * Increments the number of games lost by 1 (or a specified value).
   *
   * @param gamesLost The number of games lost to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addGamesLost(gamesLost: Int = 1)

  /**
   * @since 0.1.0
   */
  fun subtractPlayedGames(playedGames: Int = 1)

  /**
   * @since 0.1.0
   */
  fun subtractGamesWon(gamesWon: Int = 1)

  /**
   * @since 0.1.0
   */
  fun subtractGamesLost(gamesLost: Int = 1)
}