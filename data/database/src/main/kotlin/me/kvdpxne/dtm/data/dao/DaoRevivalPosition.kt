package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.extensions.mapping.toRawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.repositories.RepositoryRevivalPosition
import me.kvdpxne.dtm.data.tables.TableRevivalPosition
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
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

object DaoRevivalPosition : RepositoryRevivalPosition {

  /**
   * @since 0.1.0
   */
  private val OP_COLUMNS: List<Column<*>> by lazy {
    arrayListOf(
      TableRevivalPosition.identifier,
      TableRevivalPosition.teamIdentifier,
      TableRevivalPosition.x,
      TableRevivalPosition.y,
      TableRevivalPosition.z,
      TableRevivalPosition.pitch,
      TableRevivalPosition.yaw,
      *DaoTeam.COLUMNS
    )
  }

  override suspend fun readRevivalPositions(): Collection<RawRevivalPosition> {
    return concurrentTransaction {
      TableRevivalPosition
        .innerJoin(TableTeam)
        .select(OP_COLUMNS)
        .map { row: ResultRow ->
          row.toRawRevivalPosition()
        }
        .toList()
    }
  }

  override suspend fun readRevivalPositionIdentifiers(): Collection<UUID> {
    return concurrentTransaction {
      TableRevivalPosition
        .select(TableRevivalPosition.identifier)
        .map { row: ResultRow ->
          row[TableRevivalPosition.identifier]
        }
        .toList()
    }
  }

  override suspend fun findRevivalPositionByIdentifierOrNull(
    identifier: UUID
  ): RawRevivalPosition? {
    return concurrentTransaction {
      TableRevivalPosition
        .innerJoin(TableTeam)
        .select(OP_COLUMNS)
        .where {
          TableRevivalPosition.identifier eq identifier
        }
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toRawRevivalPosition()
        }
    }
  }

  /**
   * @since 0.1.0
   */
  private fun exists(
    identifier: UUID
  ): Boolean {
    return !TableRevivalPosition
      .select(TableRevivalPosition.identifier)
      .where {
        TableRevivalPosition.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsRevivalPositionByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      this@DaoRevivalPosition.exists(identifier)
    }
  }

  /**
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    revivalPosition: RawRevivalPosition,
    builder: UpdateBuilder<*>
  ) {
    TableRevivalPosition.run {
      builder[this.teamIdentifier] = revivalPosition.team.identifier
      builder[this.x] = revivalPosition.x
      builder[this.y] = revivalPosition.y
      builder[this.z] = revivalPosition.z
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
    builder[TableRevivalPosition.identifier] = revivalPosition.identifier
    this.fillUpdateStatement(revivalPosition, builder)
  }

  override suspend fun insertRevivalPosition(
    revivalPosition: RawRevivalPosition?
  ): Int {
    val result: Int = revivalPosition.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == revivalPosition) {
      return result
    }

    return concurrentTransaction {
      // A new record cannot be inserted into a database table if a previously
      // inserted record has the same identifier as the record to be inserted.
      if (this@DaoRevivalPosition.exists(revivalPosition.identifier)) {
        return@concurrentTransaction ResponseCodes.DUPLICATED
      }

      // A new record cannot be inserted into a table in the database if the
      // team identifier has no reference to a record in the teams table.
      if (!DaoTeam.exists(revivalPosition.team.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_REFERENCE
      }

      TableRevivalPosition.insert { statement: InsertStatement<*> ->
        this@DaoRevivalPosition.fillInsertStatement(
          revivalPosition,
          statement
        )
      }.insertedCount
    }
  }

  override suspend fun updateRevivalPosition(
    revivalPosition: RawRevivalPosition?
  ): Int {
    val result: Int = revivalPosition.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == revivalPosition) {
      return result
    }

    return concurrentTransaction {
      if (!this@DaoRevivalPosition.exists(revivalPosition.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD
      }

      if (!DaoTeam.exists(revivalPosition.team.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_REFERENCE
      }

      TableRevivalPosition.update({
        TableRevivalPosition.identifier eq revivalPosition.identifier
      }) { statement: UpdateStatement ->
        this@DaoRevivalPosition.fillUpdateStatement(
          revivalPosition,
          statement
        )
      }
    }
  }

  override suspend fun deleteRevivalPositionByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      TableRevivalPosition.deleteWhere { _: ISqlExpressionBuilder ->
        TableRevivalPosition.identifier eq identifier
      }
    }
  }

  override suspend fun truncateRevivalPositions(): Int {
    return concurrentTransaction {
      TableRevivalPosition.deleteAll()
    }
  }

  override suspend fun countRevivalPositions(): Long {
    return concurrentTransaction {
      TableRevivalPosition.selectAll().count()
    }
  }
}