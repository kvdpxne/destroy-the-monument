package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserStatistics

/**
 * @since 0.1.0
 */
interface RepositoryUserStatistics {

  /**
   * @since 0.1.0
   */
  suspend fun findUserStatisticsByIdentifier(
    identifier: UUID
  ): RawUserStatistics?

  /**
   * @since 0.1.0
   */
  suspend fun insertUserStatistics(
    userStatistics: RawUserStatistics
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateUserStatistics(
    userStatistics: RawUserStatistics
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteUserStatisticsByIdentifier(
    identifier: UUID
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteUserStatistics(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countUserStatistics(): Long
}