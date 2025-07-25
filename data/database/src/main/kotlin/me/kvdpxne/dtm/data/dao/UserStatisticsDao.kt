package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.toRawUserStatistics
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.repositories.UserStatisticsRepository
import me.kvdpxne.dtm.data.tables.UserStatisticsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateUserStatistics
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.updateReturning

object UserStatisticsDao : UserStatisticsRepository {

  override suspend fun findUserStatisticsByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawUserStatistics, ValidationResult>? {
    return concurrentTransaction(readOnly = true) {
      UserStatisticsTable
        .selectAll()
        .where {
          UserStatisticsTable.identifier eq identifier
        }
        .firstNotNullOfOrNull(ResultRow::toRawUserStatistics)
    }
  }

  /**
   * @since 0.1.0
   */
  private fun exists(
    identifier: UUID
  ): Boolean {
    return !UserStatisticsTable
      .select(UserStatisticsTable.identifier)
      .where {
        UserStatisticsTable.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsUserStatisticsByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      this@UserStatisticsDao.exists(identifier)
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
    builder[UserStatisticsTable.identifier] = userStatistics.identifier
    this.fillUpdateStatement(userStatistics, builder)
  }

  /**
   * @param userStatistics
   *
   * @since 0.1.0
   */
  suspend fun insertUserStatisticsWithoutValidation(
    userStatistics: RawUserStatistics
  ): Pair<RawUserStatistics?, ValidationResult> {
    return concurrentTransaction {
      if (this@UserStatisticsDao.exists(userStatistics.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "User statistics already exists",
          ResponseCodes.DUPLICATED,
          userStatistics.identifier
        )
      }

      UserStatisticsTable
        .insertReturning { statement: InsertStatement<*> ->
          this@UserStatisticsDao.fillInsertStatement(userStatistics, statement)
        }
        .firstNotNullOf(ResultRow::toRawUserStatistics)
    }
  }

  override suspend fun insertUserStatistics(
    userStatistics: RawUserStatistics?
  ): Pair<RawUserStatistics?, ValidationResult> {
    val result: ValidationResult = validateUserStatistics(userStatistics)
    return if (!result.isValid || null == userStatistics) failure(result)
    else this.insertUserStatisticsWithoutValidation(userStatistics)
  }

  /**
   * @param userStatistics
   *
   * @since 0.1.0
   */
  suspend fun updateUserStatisticsWithoutValidation(
    userStatistics: RawUserStatistics
  ): Pair<RawUserStatistics?, ValidationResult> {
    return concurrentTransaction {
      if (!this@UserStatisticsDao.exists(userStatistics.identifier)) {
        return@concurrentTransaction failure(
          Efn.IDENTIFIER,
          "User statistics does not exist",
          ResponseCodes.NO_RECORD,
          userStatistics.identifier
        )
      }

      UserStatisticsTable
        .updateReturning(where = {
          UserStatisticsTable.identifier eq userStatistics.identifier
        }) { statement: UpdateStatement ->
          this@UserStatisticsDao.fillUpdateStatement(userStatistics, statement)
        }
        .firstNotNullOf(ResultRow::toRawUserStatistics)
    }
  }

  override suspend fun updateUserStatistics(
    userStatistics: RawUserStatistics?
  ): Pair<RawUserStatistics?, ValidationResult> {
    val result: ValidationResult = validateUserStatistics(userStatistics)
    return if (!result.isValid || null == userStatistics) failure(result)
    else this.insertUserStatisticsWithoutValidation(userStatistics)
  }

  override suspend fun deleteUserStatisticsByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      UserStatisticsTable.deleteWhere {
        this.identifier eq identifier
      }
    }
  }

  override suspend fun truncateUserStatistics(): Int {
    return concurrentTransaction {
      UserStatisticsTable.deleteAll()
    }
  }

  override suspend fun countUserStatistics(): Long {
    return concurrentTransaction(readOnly = true) {
      UserStatisticsTable
        .select(UserStatisticsTable.identifier)
        .count()
    }
  }
}