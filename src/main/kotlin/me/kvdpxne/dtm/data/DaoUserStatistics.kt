package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableUserStatistics
import me.kvdpxne.dtm.user.UserStatistics
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoUserStatistics {

  /**
   * @since 0.1.0
   */
  private fun toUserStatistics(
    row: QueryRowSet
  ): UserStatistics {
    //
    val identifier = row[TableUserStatistics.identifier]!!

    //
    val kills = row[TableUserStatistics.kills]!!
    val assists = row[TableUserStatistics.assists]!!
    val deaths = row[TableUserStatistics.deaths]!!
    val destroyedMonuments = row[TableUserStatistics.destroyedMonuments]!!
    val playedGames = row[TableUserStatistics.playedGames]!!
    val gamesWon = row[TableUserStatistics.gamesWon]!!
    val gamesLost = row[TableUserStatistics.gamesLost]!!

    //
    return UserStatistics(
      kills,
      assists,
      deaths,
      destroyedMonuments,
      playedGames,
      gamesWon,
      gamesLost,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun findUserStatisticsByIdentifierOrNull(
    identifier: String
  ): UserStatistics? {
    return database.from(TableUserStatistics)
      .select()
      .where {
        TableUserStatistics.identifier eq identifier
      }
      .map {
        toUserStatistics(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertUserStatistics(
    statistics: UserStatistics
  ) {
    database.insert(TableUserStatistics) {
      set(it.identifier, statistics.identifier)
      set(it.kills, statistics.kills)
      set(it.assists, statistics.assists)
      set(it.deaths, statistics.deaths)
      set(it.destroyedMonuments, statistics.destroyedMonuments)
      set(it.playedGames, statistics.playedGames)
      set(it.gamesWon, statistics.gamesWon)
      set(it.gamesLost, statistics.gamesLost)
    }
  }

  /**
   * @since 0.1.0
   */
  fun updateUserStatistics(
    statistics: UserStatistics
  ) {
    database.update(TableUserStatistics) {
      set(it.kills, statistics.kills)
      set(it.assists, statistics.assists)
      set(it.deaths, statistics.deaths)
      set(it.destroyedMonuments, statistics.destroyedMonuments)
      set(it.playedGames, statistics.playedGames)
      set(it.gamesWon, statistics.gamesWon)
      set(it.gamesLost, statistics.gamesLost)

      where {
        it.identifier eq statistics.identifier
      }
    }
  }
}