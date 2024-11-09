package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.user.UserStatistics

/**
 * Repository interface for managing user statistics data operations.
 *
 * The [UserStatisticsRepository] interface provides methods to insert and
 * update statistics associated with users. Implementations of this interface
 * should handle the underlying data store specifics, allowing for efficient
 * persistence and retrieval of user performance data such as kills, deaths,
 * assists, etc.
 *
 * @since 0.1.0
 */
interface UserStatisticsRepository {

  /**
   * Inserts new statistics data for a user.
   *
   * This method takes a [UserStatistics] object and persists it to the
   * data store, typically associating it with a specific user. The return
   * value indicates the result of the insert operation, commonly represented
   * by the number of rows affected or an identifier for the inserted
   * statistics entry.
   *
   * @param userStatistics The statistics data to be inserted.
   *
   * @return An integer representing the result of the insert operation,
   *         such as the number of rows affected.
   *
   * @since 0.1.0
   */
  suspend fun insertUserStatistics(
    userStatistics: UserStatistics
  ): Int

  /**
   * Updates existing statistics data for a user.
   *
   * This method modifies an existing [UserStatistics] entry in the data
   * store with new or modified statistics data. The return value reflects the
   * result of the update operation, typically the number of rows affected.
   *
   * @param userStatistics The updated statistics data to be persisted.
   *
   * @return An integer representing the result of the update operation,
   *         such as the number of rows affected.
   *
   * @since 0.1.0
   */
  suspend fun updateUserStatistics(
    userStatistics: UserStatistics
  ): Int
}