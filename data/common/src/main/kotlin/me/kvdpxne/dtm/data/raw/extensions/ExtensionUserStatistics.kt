package me.kvdpxne.dtm.data.raw.extensions

import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.user.statistics.UserStatistics

/**
 * Extension function for mapping a [UserStatistics] object to a
 * [RawUserStatistics] object.
 *
 * This function ensures that all fields in the [UserStatistics] object are
 * validated before converting the data to a format suitable for database
 * operations. The validation checks prevent negative values in
 * statistics-related fields, maintaining data integrity.
 *
 * @since 0.1.0
 */
@Synchronized
fun UserStatistics.toRawUserStatistics(): RawUserStatistics {
  require(0 <= this.kills) {
    "The number of kills cannot be negative."
  }

  require(0 <= this.assists) {
    "The number of assists cannot be negative."
  }

  require(0 <= this.deaths) {
    "The number of deaths cannot be negative."
  }

  require(0 <= this.destroyedMonuments) {
    "The number of destroyed monuments cannot be negative."
  }

  require(0 <= this.playedGames) {
    "The number of played games cannot be negative."
  }

  require(0 <= this.gamesWon) {
    "The number of games won cannot be negative."
  }

  require(0 <= this.gamesLost) {
    "The number of games lost cannot be negative."
  }

  return RawUserStatistics(
    this.identifier,
    this.kills,
    this.assists,
    this.deaths,
    this.destroyedMonuments,
    this.playedGames,
    this.gamesWon,
    this.gamesLost
  )
}