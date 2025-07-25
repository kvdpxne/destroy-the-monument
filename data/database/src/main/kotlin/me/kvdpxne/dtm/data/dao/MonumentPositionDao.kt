package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.toRawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.repositories.MonumentPositionRepository
import me.kvdpxne.dtm.data.tables.MonumentPositionTable
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.BasicValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateMonumentPosition
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

/**
 * @since 0.1.0
 */
object MonumentPositionDao : MonumentPositionRepository {

  override suspend fun readMonumentPositions(): Collection<Pair<RawMonumentPosition, ValidationResult>> {
    return concurrentTransaction(readOnly = true) {
      MonumentPositionTable
        .innerJoin(TeamTable)
        .selectAll()
        .map(ResultRow::toRawMonumentPosition)
        .toList()
    }
  }

  override suspend fun readMonumentPositionIdentifiers(): Collection<UUID> {
    return concurrentTransaction(readOnly = true) {
      MonumentPositionTable
        .select(MonumentPositionTable.identifier)
        .map { row: ResultRow ->
          row[MonumentPositionTable.identifier]
        }
        .toList()
    }
  }

  override suspend fun findMonumentPositionByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawMonumentPosition, ValidationResult>? {
    return concurrentTransaction(readOnly = true) {
      MonumentPositionTable
        .innerJoin(TeamTable)
        .selectAll()
        .where {
          MonumentPositionTable.identifier eq identifier
        }
        .firstNotNullOfOrNull(ResultRow::toRawMonumentPosition)
    }
  }

  /**
   * @since 0.1.0
   */
  internal fun exists(
    identifier: UUID
  ): Boolean {
    return !MonumentPositionTable
      .select(MonumentPositionTable.identifier)
      .where {
        MonumentPositionTable.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsMonumentPositionByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      this@MonumentPositionDao.exists(identifier)
    }
  }

  /**
   * @param monumentPosition
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    monumentPosition: RawMonumentPosition,
    builder: UpdateBuilder<*>
  ) {
    MonumentPositionTable.run {
      builder[this.teamIdentifier] = monumentPosition.team.identifier
      builder[this.x] = monumentPosition.x
      builder[this.y] = monumentPosition.y
      builder[this.z] = monumentPosition.z
    }
  }

  /**
   * @param monumentPosition
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    monumentPosition: RawMonumentPosition,
    builder: UpdateBuilder<*>
  ) {
    builder[MonumentPositionTable.identifier] = monumentPosition.identifier
    this.fillUpdateStatement(monumentPosition, builder)
  }

  override suspend fun insertMonumentPosition(
    monumentPosition: RawMonumentPosition?
  ): Pair<RawMonumentPosition?, ValidationResult> {
    val result: ValidationResult = validateMonumentPosition(monumentPosition)
    if (!result.isValid || null == monumentPosition) {
      return failure(result)
    }

    return concurrentTransaction {
      if (this@MonumentPositionDao.exists(monumentPosition.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "",
          ResponseCodes.DUPLICATED,
          monumentPosition.identifier
        )
      }

      val team: Pair<RawTeam?, ValidationResult> =
        TeamDao.insertTeamWithoutValidation(
          monumentPosition.team
        )

      val teamResult: ValidationResult = team.second
      if (teamResult is BasicValidationResult.Failure
        && teamResult.hasErrorByCode(ResponseCodes.DUPLICATED)
        && (null == team.first || !teamResult.isValid)
      ) return@concurrentTransaction failure(teamResult)


      MonumentPositionTable
        .insertReturning { statement: InsertStatement<*> ->
          this@MonumentPositionDao.fillInsertStatement(monumentPosition, statement)
        }
        .firstNotNullOf { row: ResultRow ->
          row.toRawMonumentPosition(team = team)
        }
    }
  }


  override suspend fun updateMonumentPosition(
    monumentPosition: RawMonumentPosition?
  ): Pair<RawMonumentPosition?, ValidationResult> {
    val result: ValidationResult = validateMonumentPosition(monumentPosition)
    if (!result.isValid || null == monumentPosition) {
      return failure(result)
    }

    return concurrentTransaction {
      if (!this@MonumentPositionDao.exists(monumentPosition.identifier)) {
        return@concurrentTransaction failure(
          Efn.IDENTIFIER,
          "",
          ResponseCodes.NO_RECORD,
          monumentPosition.identifier
        )
      }

      val team: Pair<RawTeam?, ValidationResult> =
        TeamDao.insertTeamWithoutValidation(monumentPosition.team)
      val teamResult: ValidationResult = team.second

      if (teamResult is BasicValidationResult.Failure
        && teamResult.hasErrorByCode(ResponseCodes.DUPLICATED)
        && ((null == team.first || !teamResult.isValid))
      ) return@concurrentTransaction failure(team.second)

      MonumentPositionTable
        .updateReturning(where = {
          MonumentPositionTable.identifier eq monumentPosition.identifier
        }) { statement: UpdateStatement ->
          this@MonumentPositionDao.fillUpdateStatement(monumentPosition, statement)
        }.firstNotNullOf { row: ResultRow ->
          row.toRawMonumentPosition(team = team)
        }
    }
  }

  override suspend fun deleteMonumentPositionByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      MonumentPositionTable.deleteWhere { _: ISqlExpressionBuilder ->
        MonumentPositionTable.identifier eq identifier
      }
    }
  }

  override suspend fun truncateMonumentPositions(): Int {
    return concurrentTransaction {
      MonumentPositionTable.deleteAll()
    }
  }

  override suspend fun countMonumentPositions(): Long {
    return concurrentTransaction(readOnly = true) {
      MonumentPositionTable
        .select(MonumentPositionTable.identifier)
        .count()
    }
  }
}