package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.statistics.IdentifiableStatistics

interface UserStatistics : IdentifiableStatistics {

  val playedGames: Int

  val gamesWon: Int

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
}