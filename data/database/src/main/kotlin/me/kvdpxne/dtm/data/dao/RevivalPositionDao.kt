package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.toRawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.repositories.RevivalPositionRepository
import me.kvdpxne.dtm.data.tables.RevivalPositionTable
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.BasicValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateRevivalPosition
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
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

object RevivalPositionDao : RevivalPositionRepository {

  override suspend fun readRevivalPositions(): Collection<Pair<RawRevivalPosition, ValidationResult>> {
    return concurrentTransaction {
      RevivalPositionTable
        .innerJoin(TeamTable)
        .selectAll()
        .map(ResultRow::toRawRevivalPosition)
        .toList()
    }
  }

  override suspend fun readRevivalPositionIdentifiers(): Collection<UUID> {
    return concurrentTransaction {
      RevivalPositionTable
        .select(RevivalPositionTable.identifier)
        .map { row: ResultRow ->
          row[RevivalPositionTable.identifier]
        }
        .toList()
    }
  }

  override suspend fun findRevivalPositionByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawRevivalPosition, ValidationResult>? {
    return concurrentTransaction {
      RevivalPositionTable
        .innerJoin(TeamTable)
        .selectAll()
        .where {
          RevivalPositionTable.identifier eq identifier
        }
        .firstNotNullOfOrNull(ResultRow::toRawRevivalPosition)
    }
  }

  /**
   * @since 0.1.0
   */
  internal fun exists(
    identifier: UUID
  ): Boolean {
    return !RevivalPositionTable
      .select(RevivalPositionTable.identifier)
      .where {
        RevivalPositionTable.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsRevivalPositionByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      this@RevivalPositionDao.exists(identifier)
    }
  }

  /**
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    revivalPosition: RawRevivalPosition,
    builder: UpdateBuilder<*>
  ) {
    RevivalPositionTable.run {
      builder[this.teamIdentifier] = revivalPosition.team.identifier
      builder[this.x] = revivalPosition.x
      builder[this.z] = revivalPosition.z
      builder[this.y] = revivalPosition.y
      builder[this.pitch] = revivalPosition.pitch
      builder[this.yaw] = revivalPosition.yaw
    }
  }

  /**
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    revivalPosition: RawRevivalPosition,
    builder: UpdateBuilder<*>
  ) {
    builder[RevivalPositionTable.identifier] = revivalPosition.identifier
    this.fillUpdateStatement(revivalPosition, builder)
  }

  override suspend fun insertRevivalPosition(
    revivalPosition: RawRevivalPosition?
  ): Pair<RawRevivalPosition?, ValidationResult> {
    val result: ValidationResult = validateRevivalPosition(revivalPosition, false)
    if (!result.isValid || null == revivalPosition) {
      return failure(result)
    }

    return concurrentTransaction {
      if (this@RevivalPositionDao.exists(revivalPosition.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "",
          ResponseCodes.DUPLICATED,
          revivalPosition.identifier
        )
      }

      val team: Pair<RawTeam?, ValidationResult> =
        TeamDao.insertTeamWithoutValidation(
          revivalPosition.team
        )

      val teamResult: ValidationResult = team.second
      if (teamResult is BasicValidationResult.Failure
        && teamResult.hasErrorByCode(ResponseCodes.DUPLICATED)
        && ((null == team.first || !teamResult.isValid))
      ) return@concurrentTransaction failure(team.second)

      RevivalPositionTable
        .insertReturning { statement: InsertStatement<*> ->
          this@RevivalPositionDao.fillInsertStatement(revivalPosition, statement)
        }
        .firstNotNullOf { row: ResultRow ->
          row.toRawRevivalPosition(team = team)
        }
    }
  }

  override suspend fun updateRevivalPosition(
    revivalPosition: RawRevivalPosition?
  ): Pair<RawRevivalPosition?, ValidationResult> {
    val result: ValidationResult = validateRevivalPosition(revivalPosition, false)
    if (!result.isValid || null == revivalPosition) {
      return failure(result)
    }

    return concurrentTransaction {
      if (!this@RevivalPositionDao.exists(revivalPosition.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "",
          ResponseCodes.NO_RECORD,
          revivalPosition.identifier
        )
      }

      val team: Pair<RawTeam?, ValidationResult> =
        TeamDao.insertTeamWithoutValidation(revivalPosition.team)
      val teamResult: ValidationResult = team.second

      if (teamResult is BasicValidationResult.Failure
        && teamResult.hasErrorByCode(ResponseCodes.DUPLICATED)
        && ((null == team.first || !teamResult.isValid))
      ) return@concurrentTransaction failure(team.second)

      RevivalPositionTable
        .updateReturning(where = {
          RevivalPositionTable.identifier eq revivalPosition.identifier
        }) { statement: UpdateStatement ->
          this@RevivalPositionDao.fillUpdateStatement(revivalPosition, statement)
        }
        .firstNotNullOf { row: ResultRow ->
          row.toRawRevivalPosition(team = team)
        }
    }
  }

  override suspend fun deleteRevivalPositionByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      RevivalPositionTable.deleteWhere { _: ISqlExpressionBuilder ->
        RevivalPositionTable.identifier eq identifier
      }
    }
  }

  override suspend fun truncateRevivalPositions(): Int {
    return concurrentTransaction {
      RevivalPositionTable.deleteAll()
    }
  }

  override suspend fun countRevivalPositions(): Long {
    return concurrentTransaction(readOnly = true) {
      RevivalPositionTable
        .select(RevivalPositionTable.identifier)
        .count()
    }
  }
}