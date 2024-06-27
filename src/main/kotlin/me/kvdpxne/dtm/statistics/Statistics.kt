package me.kvdpxne.dtm.statistics

/**
 * Represents a user's statistics.
 *
 * @param kills The number of kills the player has achieved.
 * @param assists The number of assists the player has achieved.
 * @param deaths The number of times the player has died.
 * @param playedGames The total number of games the player has played.
 * @param gamesWon The number of games the player has won.
 * @param gamesLost The number of games the player has lost.
 * @param destroyedMonuments The number of monuments the player has destroyed.
 *
 * @since 0.1.0
 */
class Statistics(
  // @formatter:off
  var kills             : Int = 0,
  var assists           : Int = 0,
  var deaths            : Int = 0,
  var playedGames       : Int = 0,
  var gamesWon          : Int = 0,
  var gamesLost         : Int = 0,
  var destroyedMonuments: Int = 0
  // @formatter:on
) {

  /**
   * Ensures the sum of kills and value doesn't go below zero.
   *
   * @param current The current value (kills, deaths, etc.)
   * @param value The value to be added
   * @return The new value after adding, capped at 0
   *
   * @since 0.1.0
   */
  private fun plus(current: Int, value: Int): Int {
    if (0 == value) {
      return current
    }
    var newValue = current + value
    if (0 > newValue) {
      newValue = 0
    }
    return newValue
  }

  /**
   * Increments the number of kills by 1 (or a specified value).
   *
   * @param kills The number of kills to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addKills(kills: Int = 1) {
    this.kills = this.plus(this.kills, kills)
  }

  /**
   * Increments the number of assists by 1 (or a specified value).
   *
   * @param assists The number of assists to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addAssists(assists: Int = 1) {
    this.assists = this.plus(this.assists, assists)
  }

  /**
   * Increments the number of deaths by 1 (or a specified value).
   *
   * @param deaths The number of deaths to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addDeaths(deaths: Int = 1) {
    this.deaths = this.plus(this.deaths, deaths)
  }

  /**
   * Increments the number of played games by 1 (or a specified value).
   *
   * @param playedGames The number of played games to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addPlayedGames(playedGames: Int = 1) {
    this.playedGames = this.plus(this.playedGames, playedGames)
  }

  /**
   * Increments the number of games won by 1 (or a specified value).
   *
   * @param gameWon The number of games won to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addGamesWon(gameWon: Int = 1) {
    this.gamesWon = this.plus(this.gamesWon, gameWon)
  }

  /**
   * Increments the number of games lost by 1 (or a specified value).
   *
   * @param gameLost The number of games lost to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addGamesLost(gameLost: Int = 1) {
    this.gamesLost = this.plus(this.gamesLost, gameLost)
  }

  /**
   * Increments the number of destroyed monuments by 1 (or a specified value).
   *
   * @param destroyedMonuments The number of destroyed monuments to add (defaults to 1)
   *
   * @since 0.1.0
   */
  fun addDestroyedMonuments(destroyedMonuments: Int = 1) {
    this.destroyedMonuments = this.plus(this.destroyedMonuments, destroyedMonuments)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Statistics

    if (kills != other.kills) return false
    if (assists != other.assists) return false
    if (deaths != other.deaths) return false
    if (playedGames != other.playedGames) return false
    if (gamesWon != other.gamesWon) return false
    if (gamesLost != other.gamesLost) return false
    if (destroyedMonuments != other.destroyedMonuments) return false

    return true
  }

  override fun hashCode(): Int {
    var result = kills
    result = 31 * result + assists
    result = 31 * result + deaths
    result = 31 * result + playedGames
    result = 31 * result + gamesWon
    result = 31 * result + gamesLost
    result = 31 * result + destroyedMonuments
    return result
  }
}