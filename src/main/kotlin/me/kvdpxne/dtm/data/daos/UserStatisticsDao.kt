package me.kvdpxne.dtm.data.daos

import me.kvdpxne.dtm.data.repositories.UserStatisticsRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.UserStatisticsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.user.UserStatistics
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

object UserStatisticsDao : UserStatisticsRepository {

  /**
   * @param userStatistics
   * @param builder
   *
   * @since 0.1.0
   */
  private fun buildUserStatisticsStatement(
    userStatistics: UserStatistics,
    builder: UpdateBuilder<Int>
  ) {
    UserStatisticsTable.run {
      builder[this.kills] = userStatistics.kills
      builder[this.assists] = userStatistics.assists
      builder[this.deaths] = userStatistics.deaths
      builder[this.destroyedMonuments] = userStatistics.destroyedMonuments
      builder[this.playedGames] = userStatistics.playedGames
      builder[this.gamesWon] = userStatistics.gamesWon
      builder[this.gamesLost] = userStatistics.gamesLost
    }
  }

  override suspend fun insertUserStatistics(
    userStatistics: UserStatistics
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserStatisticsTable.insert { it: InsertStatement<Number> ->
        it[this.identifier] = userStatistics.identifier
        buildUserStatisticsStatement(userStatistics, it)
      }.insertedCount
    }
  }

  override suspend fun updateUserStatistics(
    userStatistics: UserStatistics
  ): Int {
    if (!userStatistics.wasModified) {
      return 0
    }

    return concurrentTransaction(DatabasesConfiguration.main) {
      UserStatisticsTable.update({
        UserStatisticsTable.identifier eq userStatistics.identifier
      }) { it: UpdateStatement ->
        buildUserStatisticsStatement(userStatistics, it)
      }
    }
  }
}