package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.extensions.mapping.toRawUserStatistics
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.repositories.RepositoryUserStatistics
import me.kvdpxne.dtm.data.tables.TableUserStatistics
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.context.extensions.validate
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

object DaoUserStatistics : RepositoryUserStatistics {

  override suspend fun findUserStatisticsByIdentifierOrNull(
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

  /**
   * @since 0.1.0
   */
  private fun exists(
    identifier: UUID
  ): Boolean {
    return !TableUserStatistics
      .select(TableUserStatistics.identifier)
      .where {
        TableUserStatistics.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsUserStatisticsByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      this@DaoUserStatistics.exists(identifier)
    }
  }

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
    this@DaoUserStatistics.fillUpdateStatement(userStatistics, builder)
  }

  /**
   * @since 0.1.0
   */
  internal fun justInsertUserStatistics(
    userStatistics: RawUserStatistics
  ): Int {
    return TableUserStatistics.insert { statement: InsertStatement<*> ->
      this@DaoUserStatistics.fillInsertStatement(
        userStatistics,
        statement
      )
    }.insertedCount
  }

  override suspend fun insertUserStatistics(
    userStatistics: RawUserStatistics?
  ): Int {
    val result: Int = userStatistics.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == userStatistics) {
      return result
    }

    return concurrentTransaction {
      // A new record cannot be inserted into a database table if a previously
      // inserted record has the same identifier as the record to be inserted.
      if (this@DaoUserStatistics.exists(userStatistics.identifier)) {
        return@concurrentTransaction ResponseCodes.DUPLICATED
      }

      this@DaoUserStatistics.justInsertUserStatistics(userStatistics)
    }
  }

  override suspend fun updateUserStatistics(
    userStatistics: RawUserStatistics?
  ): Int {
    val result: Int = userStatistics.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == userStatistics) {
      return result
    }

    return concurrentTransaction {
      if (!this@DaoUserStatistics.exists(userStatistics.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD
      }

      TableUserStatistics.update({
        TableUserStatistics.identifier eq userStatistics.identifier
      }) { statement: UpdateStatement ->
        this@DaoUserStatistics.fillUpdateStatement(
          userStatistics,
          statement
        )
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

  override suspend fun truncateUserStatistics(): Int {
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