package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.statistics.BaseIdentifiableStatistics

/**
 * A class representing statistics for a user in the game.
 *
 * This class inherits from `BaseIdentifiableStatistics` and provides additional
 * statistics specific to user performance such as played games, games won, and
 * games lost. It also defines methods for incrementing each of these values.
 *
 * @param kills The initial number of kills (default 0).
 * @param assists The initial number of assists (default 0).
 * @param deaths The initial number of deaths (default 0).
 * @param destroyedMonuments The initial number of destroyed monuments (default 0).
 * @param playedGames The initial number of played games (default 0).
 * @param gamesWon The initial number of games won (default 0).
 * @param gamesLost The initial number of games lost (default 0).
 *
 * @since 0.1.0
 */
class UserStatistics(
  // @formatter:off
      kills             : Int = 0,
      assists           : Int = 0,
      deaths            : Int = 0,
      destroyedMonuments: Int = 0,
  var playedGames       : Int = 0,
  var gamesWon          : Int = 0,
  var gamesLost         : Int = 0,
      identifier        : String = UUID.randomUUID().toString(),
  // @formatter:on
): BaseIdentifiableStatistics(kills, assists, deaths, destroyedMonuments, identifier) {

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
  fun addPlayedGames(playedGames: Int = 1) {
    this.playedGames = this.add(this.playedGames, playedGames)
  }

  /**
   * Increments the number of games won by 1 (or a specified value).
   *
   * @param gameWon The number of games won to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addGamesWon(gameWon: Int = 1) {
    this.gamesWon = this.add(this.gamesWon, gameWon)
  }

  /**
   * Increments the number of games lost by 1 (or a specified value).
   *
   * @param gameLost The number of games lost to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addGamesLost(gameLost: Int = 1) {
    this.gamesLost = this.add(this.gamesLost, gameLost)
  }
}