package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.validation.ValidationResult

/**
 * Provides data access operations for user statistics entities.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface UserStatisticsRepository {

  /**
   * Finds user statistics by unique identifier.
   * @param identifier UUID of the statistics to find
   * @return Found [RawUserStatistics] or `null` if not found
   * @since 0.1.0
   */
  suspend fun findUserStatisticsByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawUserStatistics, ValidationResult>?

  /**
   * Checks existence of statistics by identifier.
   * @param identifier UUID to check
   * @return `true` if statistics exist, `false` otherwise
   * @since 0.1.0
   */
  suspend fun containsUserStatisticsByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * Inserts new user statistics after validation.
   *
   * Return value specifics:
   * - `1`: Statistics successfully inserted
   * - `0`: No operation performed (no error)
   * - Negative: Validation or database error code
   *
   * Possible error codes:
   * - [INVALID_USER_STATISTICS_KILLS]
   * - [INVALID_USER_STATISTICS_ASSISTS]
   * - [INVALID_USER_STATISTICS_DEATHS]
   * - [INVALID_USER_STATISTICS_DESTROYED_MONUMENTS]
   * - [INVALID_USER_STATISTICS_PLAYED_GAMES]
   * - [INVALID_USER_STATISTICS_GAMES_WON]
   * - [INVALID_USER_STATISTICS_GAMES_LOST]
   * - `DUPLICATED`
   * - `NO_TABLE`
   *
   * @param userStatistics Statistics data to insert
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun insertUserStatistics(
    userStatistics: RawUserStatistics?
  ): Pair<RawUserStatistics?, ValidationResult>

  /**
   * Updates existing user statistics after validation.
   *
   * Return value specifics:
   * - Positive: Number of updated records (typically 1)
   * - `0`: Statistics not found or no changes made
   * - Negative: Validation or database error code
   *
   * Possible error codes:
   * - [INVALID_USER_STATISTICS_KILLS]
   * - [INVALID_USER_STATISTICS_ASSISTS]
   * - [INVALID_USER_STATISTICS_DEATHS]
   * - [INVALID_USER_STATISTICS_DESTROYED_MONUMENTS]
   * - [INVALID_USER_STATISTICS_PLAYED_GAMES]
   * - [INVALID_USER_STATISTICS_GAMES_WON]
   * - [INVALID_USER_STATISTICS_GAMES_LOST]
   * - `NO_RECORD`
   * - `NO_REFERENCE`
   *
   * @param userStatistics Statistics data to update
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun updateUserStatistics(
    userStatistics: RawUserStatistics?
  ): Pair<RawUserStatistics?, ValidationResult>

  /**
   * Deletes statistics by identifier.
   *
   * Return value specifics:
   * - `1`: Statistics successfully deleted
   * - `0`: Statistics not found
   * - Negative: Database error code
   *
   * Possible error codes:
   * - `NO_TABLE`
   * - `NO_REFERENCE`
   *
   * @param identifier UUID of statistics to delete
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun deleteUserStatisticsByIdentifier(
    identifier: UUID
  ): Int

  /**
   * Removes all statistics entries (use with caution).
   *
   * Return value specifics:
   * - Positive: Number of deleted records
   * - `0`: Table was empty
   * - Negative: Database error code
   *
   * Possible error codes:
   * - `NO_TABLE`
   *
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun truncateUserStatistics(): Int

  /**
   * Counts total stored statistics entries.
   * @return Total number of statistics records (never negative)
   * @since 0.1.0
   */
  suspend fun countUserStatistics(): Long
}