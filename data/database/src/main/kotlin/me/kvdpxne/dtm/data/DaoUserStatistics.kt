package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.extensions.LIMIT_TO_DELETE
import me.kvdpxne.dtm.data.extensions.UPDATE_LIMIT
import me.kvdpxne.dtm.data.repositories.RepositoryUserStatistics
import me.kvdpxne.dtm.data.shared.Problem
import me.kvdpxne.dtm.data.tables.TableUserStatistics
import me.kvdpxne.dtm.data.tables.TableUserStatistics.assists
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update

object DaoUserStatistics : RepositoryUserStatistics {

  /**
   * @param userStatistics
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    userStatistics: RawUserStatistics,
    builder: UpdateBuilder<*>
  ) {
    TableUserStatistics.run {
      builder[this.kills] = userStatistics.kills
      builder[this.assists] = userStatistics.assists
      builder[this.deaths] = userStatistics.deaths
      builder[this.destroyedMonuments] = userStatistics.destroyedMonuments
      builder[this.playedGames] = userStatistics.playedGames
      builder[this.gamesWon] = userStatistics.gamesWon
      builder[this.gamesLost] = userStatistics.gamesLost
    }
  }

  /**
   * @param userStatistics
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    userStatistics: RawUserStatistics,
    builder: UpdateBuilder<*>
  ) {
    builder[TableUserStatistics.identifier] = userStatistics.identifier
    this.fillUpdateStatement(userStatistics, builder)
  }

  /**
   * @param userStatistics
   *
   * @since 0.1.0
   */
  private fun where(
    userStatistics: RawUserStatistics
  ): SqlExpressionBuilder.() -> Op<Boolean> {
    return {
      TableUserStatistics.identifier eq userStatistics.identifier
    }
  }

  internal fun ResultRow.toRawUserStatistics(
    identifier: UUID,
  ): RawUserStatistics {
    val kills: Int = this[TableUserStatistics.kills]
    val assists = this[TableUserStatistics.assists]
    val deaths: Int = this[TableUserStatistics.deaths]
    val destroyedMonuments: Int = this[TableUserStatistics.destroyedMonuments]
    val playedGames = this[TableUserStatistics.playedGames]
    val gamesWon: Int = this[TableUserStatistics.gamesWon]
    val gamesLost: Int = this[TableUserStatistics.gamesLost]

    return RawUserStatistics(
      identifier,
      kills,
      assists,
      deaths,
      destroyedMonuments,
      playedGames,
      gamesWon,
      gamesLost
    )
  }

  private fun ResultRow.toRawUserStatistics(): RawUserStatistics {
    return this.toRawUserStatistics(
      this[TableUserStatistics.identifier]
    )
  }

  override suspend fun findUserStatisticsByIdentifier(
    identifier: UUID
  ): RawUserStatistics? {
    return concurrentTransaction {
      TableUserStatistics
        .selectAll()
        .where {
          TableUserStatistics.identifier eq identifier
        }
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toRawUserStatistics()
        }
    }
  }

  override suspend fun insertUserStatistics(
    userStatistics: RawUserStatistics
  ): Int {
    return concurrentTransaction {
      TableUserStatistics.insert { builder: UpdateBuilder<*> ->
        this@DaoUserStatistics.fillInsertStatement(
          userStatistics,
          builder
        )
      }
    }.insertedCount
  }

  override suspend fun updateUserStatistics(
    userStatistics: RawUserStatistics
  ): Int {
    return concurrentTransaction {
      TableUserStatistics.update(where(userStatistics)) {
        fillUpdateStatement(userStatistics, it)
      }
    }
  }

  override suspend fun deleteUserStatisticsByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      TableUserStatistics.deleteWhere {
        this.identifier eq identifier
      }
    }
  }

  override suspend fun deleteUserStatistics(): Int {
    return concurrentTransaction {
      TableUserStatistics.deleteAll()
    }
  }

  override suspend fun countUserStatistics(): Long {
    return concurrentTransaction {
      TableUserStatistics.selectAll().count()
    }
  }
}