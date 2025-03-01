package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.extensions.mapping.toRawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.repositories.RepositoryMonumentPosition
import me.kvdpxne.dtm.data.tables.TableMonumentPosition
import me.kvdpxne.dtm.data.tables.TableTeam
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.context.extensions.validate
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

/**
 * @since 0.1.0
 */
object DaoMonumentPosition : RepositoryMonumentPosition {

  /**
   * @since 0.1.0
   */
  private val OP_COLUMNS: List<Column<*>> by lazy {
    arrayListOf(
      TableMonumentPosition.identifier,
      TableMonumentPosition.teamIdentifier,
      TableMonumentPosition.x,
      TableMonumentPosition.y,
      TableMonumentPosition.z,
      *DaoTeam.COLUMNS
    )
  }

  override suspend fun readMonumentPositions(): Collection<RawMonumentPosition> {
    return concurrentTransaction(readOnly = true) {
      TableMonumentPosition
        .innerJoin(TableTeam)
        .select(OP_COLUMNS)
        .map { row: ResultRow ->
          row.toRawMonumentPosition()
        }
        .toList()
    }
  }

  override suspend fun readMonumentPositionIdentifiers(): Collection<UUID> {
    return concurrentTransaction(readOnly = true) {
      TableMonumentPosition
        .select(TableMonumentPosition.identifier)
        .map { row: ResultRow ->
          row[TableMonumentPosition.identifier]
        }
        .toList()
    }
  }

  override suspend fun findMonumentPositionByIdentifierOrNull(
    identifier: UUID
  ): RawMonumentPosition? {
    return concurrentTransaction(readOnly = true) {
      TableMonumentPosition
        .innerJoin(TableTeam)
        .select(OP_COLUMNS)
        .where {
          TableMonumentPosition.identifier eq identifier
        }
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toRawMonumentPosition()
        }
    }
  }

  /**
   * @since 0.1.0
   */
  private fun exists(
    identifier: UUID
  ): Boolean {
    return !TableMonumentPosition
      .select(TableMonumentPosition.identifier)
      .where {
        TableMonumentPosition.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsMonumentPositionByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      exists(identifier)
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
    TableMonumentPosition.run {
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
    builder[TableMonumentPosition.identifier] = monumentPosition.identifier
    this.fillUpdateStatement(monumentPosition, builder)
  }

  override suspend fun insertMonumentPosition(
    monumentPosition: RawMonumentPosition?
  ): Int {
    val result: Int = monumentPosition.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == monumentPosition) {
      return result
    }

    return concurrentTransaction {
      // A new record cannot be inserted into a database table if a previously
      // inserted record has the same identifier as the record to be inserted.
      if (this@DaoMonumentPosition.exists(monumentPosition.identifier)) {
        return@concurrentTransaction ResponseCodes.DUPLICATED
      }

      // A new record cannot be inserted into a table in the database if the
      // team identifier has no reference to a record in the teams table.
      if (!DaoTeam.exists(monumentPosition.team.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_REFERENCE
      }

      TableMonumentPosition.insert { statement: InsertStatement<*> ->
        fillInsertStatement(
          monumentPosition,
          statement
        )
      }.insertedCount
    }
  }

  override suspend fun updateMonumentPosition(
    monumentPosition: RawMonumentPosition?
  ): Int {
    val result: Int = monumentPosition.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == monumentPosition) {
      return result
    }

    return concurrentTransaction {
      if (!this@DaoMonumentPosition.exists(monumentPosition.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD
      }

      if (!DaoTeam.exists(monumentPosition.team.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_REFERENCE
      }

      TableMonumentPosition.update({
        TableMonumentPosition.identifier eq monumentPosition.identifier
      }) { statement: UpdateStatement ->
        fillUpdateStatement(
          monumentPosition,
          statement
        )
      }
    }
  }

  override suspend fun deleteMonumentPositionByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      TableMonumentPosition.deleteWhere { _: ISqlExpressionBuilder ->
        TableMonumentPosition.identifier eq identifier
      }
    }
  }

  override suspend fun truncateMonumentPositions(): Int {
    return concurrentTransaction {
      TableMonumentPosition.deleteAll()
    }
  }

  override suspend fun countMonumentPositions(): Long {
    return concurrentTransaction(readOnly = true) {
      TableMonumentPosition
        .select(TableMonumentPosition.identifier)
        .count()
    }
  }
}