package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.user.UserStatistics

interface UserStatisticsRepository {

  /**
   * @param userStatistics
   *
   * @since 0.1.0
   */
  suspend fun insertUserStatistics(
    userStatistics: UserStatistics
  ): Int

  /**
   * @param userStatistics
   *
   * @since 0.1.0
   */
  suspend fun updateUserStatistics(
    userStatistics: UserStatistics
  ): Int
}