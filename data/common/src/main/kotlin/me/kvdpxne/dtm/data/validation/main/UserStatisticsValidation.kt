package me.kvdpxne.dtm.data.validation.main

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.codes.StatisticsCodes
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.rules.StatisticsRules
import me.kvdpxne.dtm.data.validation.withValidationBuilder

/**
 * Validates a statistics field value according to standard rules.
 *
 * ### Validation Rule
 * - Value must be >= [StatisticsRules.MIN_VALUE] (typically 0)
 *
 * Applies to all integer-based statistics fields:
 * - Kills, assists, deaths
 * - Destroyed monuments
 * - Game participation metrics
 *
 * @param field The integer value to validate
 * @return `true` if value meets minimum requirement, `false` otherwise
 * @since 0.1.0
 */
fun isUserStatisticsFieldValid(
  field: Int
): Boolean {
  return StatisticsRules.MIN_VALUE <= field
}

/**
 * @param identifier
 * @param kills
 * @param assists
 * @param deaths
 * @param destroyedMonuments
 * @param gamesWon
 * @param gamesLost
 */
fun validateUserStatistics(
  identifier: UUID?,
  kills: Int,
  assists: Int,
  deaths: Int,
  destroyedMonuments: Int,
  playedGames: Int,
  gamesWon: Int,
  gamesLost: Int
): ValidationResult = withValidationBuilder { builder: ValidationResultBuilder ->
  if (!isUuidValid(identifier)) {
    builder.addError(
      EntityFieldNames.IDENTIFIER,
      "",
      StandardCodes.INVALID_PRIMARY_KEY,
      identifier
    )
  }

  if (!isUserStatisticsFieldValid(kills)) {
    builder.addError(
      EntityFieldNames.KILLS,
      "The number of kills cannot be negative.",
      StatisticsCodes.INVALID_KILLS,
      kills
    )
  }

  if (!isUserStatisticsFieldValid(assists)) {
    builder.addError(
      EntityFieldNames.ASSISTS,
      "The number of assists cannot be negative.",
      StatisticsCodes.INVALID_ASSISTS,
      assists
    )
  }

  if (!isUserStatisticsFieldValid(deaths)) {
    builder.addError(
      EntityFieldNames.DEATHS,
      "The number of deaths cannot be negative.",
      StatisticsCodes.INVALID_DEATHS,
      deaths
    )
  }

  if (!isUserStatisticsFieldValid(destroyedMonuments)) {
    builder.addError(
      EntityFieldNames.DESTROYED_MONUMENTS,
      "The number of destroyed monuments cannot be negative.",
      StatisticsCodes.INVALID_DESTROYED_MONUMENTS,
      destroyedMonuments
    )
  }

  if (!isUserStatisticsFieldValid(playedGames)) {
    builder.addError(
      EntityFieldNames.PLAYED_GAMES,
      "The number of played games cannot be negative.",
      StatisticsCodes.INVALID_PLAYED_GAMES,
      playedGames
    )
  }

  if (!isUserStatisticsFieldValid(gamesWon)) {
    builder.addError(
      EntityFieldNames.GAMES_WON,
      "The number of games won cannot be negative.",
      StatisticsCodes.INVALID_GAMES_WON,
      gamesWon
    )
  }

  if (!isUserStatisticsFieldValid(gamesLost)) {
    builder.addError(
      EntityFieldNames.GAMES_LOST,
      "The number of games lost cannot be negative.",
      StatisticsCodes.INVALID_GAMES_LOST,
      gamesLost
    )
  }

  builder.build()
}

/**
 * Validates a complete user statistics object.
 *
 * Performs the following checks in order:
 * 1. Null reference check
 * 2. Kills validation
 * 3. Assists validation
 * 4. Deaths validation
 * 5. Destroyed monuments validation
 * 6. Played games validation
 * 7. Games won validation
 * 8. Games lost validation
 *
 * ### Validation Flow
 * - If any check fails, returns immediately with specific error code
 * - Only returns [StandardCodes.EVERYTHING_OK] if all checks pass
 *
 * @param userStatistics The statistics object to validate
 * @return Validation result code:
 * - [StandardCodes.EVERYTHING_OK] if valid
 * - [StandardCodes.INVALID_REFERENCE] if null
 * - [StatisticsCodes.INVALID_KILLS] for invalid kills count
 * - [StatisticsCodes.INVALID_ASSISTS] for invalid assists count
 * - [StatisticsCodes.INVALID_DEATHS] for invalid deaths count
 * - [StatisticsCodes.INVALID_DESTROYED_MONUMENTS] for invalid monument count
 * - [StatisticsCodes.INVALID_PLAYED_GAMES] for invalid played games count
 * - [StatisticsCodes.INVALID_GAMES_WON] for invalid games won count
 * - [StatisticsCodes.INVALID_GAMES_LOST] for invalid games lost count
 *
 * @see isUserStatisticsFieldValid
 * @since 0.1.0
 */
fun validateUserStatistics(
  userStatistics: RawUserStatistics?
): ValidationResult = userStatistics?.run {
  validateUserStatistics(
    this.identifier,
    this.kills,
    this.assists,
    this.deaths,
    this.destroyedMonuments,
    this.playedGames,
    this.gamesWon,
    this.gamesLost
  )
} ?: withValidationBuilder { builder: ValidationResultBuilder ->
  builder.addError(
    EntityNames.USER_STATISTICS,
    "The user's raw statistics object cannot be null.",
    StandardCodes.INVALID_REFERENCE
  ).build()
}