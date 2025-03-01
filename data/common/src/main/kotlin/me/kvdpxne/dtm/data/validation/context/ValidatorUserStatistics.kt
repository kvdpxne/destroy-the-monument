package me.kvdpxne.dtm.data.validation.context

import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.INVALID_REFERENCE
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_ASSISTS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_DEATHS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_DESTROYED_MONUMENTS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_GAMES_LOST
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_GAMES_WON
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_KILLS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_PLAYED_GAMES

/**
 * Validates whether a given integer field in user statistics is valid.
 * A valid field must be greater than or equal to 0.
 *
 * @param field The integer field to validate (e.g., kills, assists, deaths, etc.).
 * @return `true` if the field is valid (i.e., greater than or equal to 0), otherwise `false`.
 * @since 0.1.0
 */
fun isUserStatisticsFieldValid(
  field: Int
): Boolean {
  return 0 <= field
}

/**
 * Validates the integrity and correctness of a [RawUserStatistics] object.
 *
 * This function checks if the provided [RawUserStatistics] is not `null` and
 * if all its fields (kills, assists, deaths, destroyed monuments, played games,
 * games won, and games lost) are valid. It returns a validation result code
 * indicating the outcome of the validation.
 *
 * @param userStatistics The [RawUserStatistics] object to validate.
 * @return An integer code representing the validation result:
 * - [EVERYTHING_OK] if the statistics are valid.
 * - [INVALID_REFERENCE] if the statistics reference is `null`.
 * - [INVALID_USER_STATISTICS_KILLS] if the kills field is invalid.
 * - [INVALID_USER_STATISTICS_ASSISTS] if the assists field is invalid.
 * - [INVALID_USER_STATISTICS_DEATHS] if the deaths field is invalid.
 * - [INVALID_USER_STATISTICS_DESTROYED_MONUMENTS] if the destroyed monuments field is invalid.
 * - [INVALID_USER_STATISTICS_PLAYED_GAMES] if the played games field is invalid.
 * - [INVALID_USER_STATISTICS_GAMES_WON] if the games won field is invalid.
 * - [INVALID_USER_STATISTICS_GAMES_LOST] if the games lost field is invalid.
 * @since 0.1.0
 */
fun isUserStatisticsValid(
  userStatistics: RawUserStatistics?
): Int {
  if (null == userStatistics) {
    return INVALID_REFERENCE
  }

  if (!isUserStatisticsFieldValid(userStatistics.kills)) {
    return INVALID_USER_STATISTICS_KILLS
  }

  if (!isUserStatisticsFieldValid(userStatistics.assists)) {
    return INVALID_USER_STATISTICS_ASSISTS
  }

  if (!isUserStatisticsFieldValid(userStatistics.deaths)) {
    return INVALID_USER_STATISTICS_DEATHS
  }

  if (!isUserStatisticsFieldValid(userStatistics.destroyedMonuments)) {
    return INVALID_USER_STATISTICS_DESTROYED_MONUMENTS
  }

  if (!isUserStatisticsFieldValid(userStatistics.playedGames)) {
    return INVALID_USER_STATISTICS_PLAYED_GAMES
  }

  if (!isUserStatisticsFieldValid(userStatistics.gamesWon)) {
    return INVALID_USER_STATISTICS_GAMES_WON
  }

  if (!isUserStatisticsFieldValid(userStatistics.gamesLost)) {
    return INVALID_USER_STATISTICS_GAMES_LOST
  }

  return EVERYTHING_OK
}