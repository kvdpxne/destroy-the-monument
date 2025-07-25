package me.kvdpxne.dtm.data.dao

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.mapToRawArena
import me.kvdpxne.dtm.data.mapping.toRawMonumentPosition
import me.kvdpxne.dtm.data.mapping.toRawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawArena
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.repositories.ArenaRepository
import me.kvdpxne.dtm.data.tables.ArenaMapTable
import me.kvdpxne.dtm.data.tables.ArenaMonumentPositionsTable
import me.kvdpxne.dtm.data.tables.ArenaRevivalPositionsTable
import me.kvdpxne.dtm.data.tables.ArenaTable
import me.kvdpxne.dtm.data.tables.MonumentPositionTable
import me.kvdpxne.dtm.data.tables.RevivalPositionTable
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.BasicValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.common.validateUuid
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateArena
import me.kvdpxne.dtm.data.validation.main.validateMonumentPosition
import me.kvdpxne.dtm.data.validation.main.validateRevivalPosition
import me.kvdpxne.dtm.data.validation.success
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.upperCase

object ArenaDao : ArenaRepository {

  /**
   * @since 0.1.0
   */
  private fun prepareQuery(): Query {
    return ArenaTable
      .innerJoin(ArenaMapTable)
      .leftJoin(ArenaMonumentPositionsTable)
      .leftJoin(MonumentPositionTable)
      .leftJoin(TeamTable, { MonumentPositionTable.teamIdentifier }, { TeamTable.identifier })
      .leftJoin(ArenaRevivalPositionsTable)
      .leftJoin(RevivalPositionTable)
      .leftJoin(TeamTable, { RevivalPositionTable.teamIdentifier }, { TeamTable.identifier })
      .selectAll()
  }

  override suspend fun readArenaIdentifiers(): Collection<UUID> {
    return concurrentTransaction(readOnly = true) {
      ArenaTable
        .select(ArenaTable.identifier)
        .map { row: ResultRow ->
          row[ArenaTable.identifier]
        }
        .toList()
    }
  }

  override suspend fun readArenaNames(): Collection<String> {
    return concurrentTransaction(readOnly = true) {
      ArenaTable
        .select(ArenaTable.name)
        .map { row: ResultRow ->
          row[ArenaTable.name].uppercase(Locale.US)
        }
        .toList()
    }
  }

  override suspend fun findArenaMonumentPositionsByIdentifier(
    identifier: UUID
  ): Collection<Pair<RawMonumentPosition, ValidationResult>> {
    return concurrentTransaction(readOnly = true) {
      ArenaMonumentPositionsTable
        .innerJoin(MonumentPositionTable)
        .innerJoin(TeamTable)
        .selectAll()
        .where {
          ArenaMonumentPositionsTable.arenaIdentifier eq identifier
        }
        .map { row: ResultRow ->
          row.toRawMonumentPosition(
            row[ArenaMonumentPositionsTable.monumentPositionIdentifier]
          )
        }
        .toList()
    }
  }

  override suspend fun findArenaRevivalPositionsByIdentifier(
    identifier: UUID
  ): Collection<Pair<RawRevivalPosition, ValidationResult>> {
    return concurrentTransaction(readOnly = true) {
      ArenaRevivalPositionsTable
        .innerJoin(RevivalPositionTable)
        .innerJoin(TeamTable)
        .selectAll()
        .where {
          ArenaRevivalPositionsTable.arenaIdentifier eq identifier
        }
        .map { row: ResultRow ->
          row.toRawRevivalPosition(
            row[ArenaRevivalPositionsTable.revivalPositionIdentifier]
          )
        }
        .toList()
    }
  }

  /**
   * @param predicate
   *
   * @since 0.1.0
   */
  private suspend fun findBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Pair<RawArena, ValidationResult>? {
    return concurrentTransaction(readOnly = true) {
      mapToRawArena(
        this@ArenaDao.prepareQuery()
          .where(predicate)
      )
    }
  }

  override suspend fun findArenaByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawArena, ValidationResult>? {
    return this.findBy {
      ArenaTable.identifier eq identifier
    }
  }

  override suspend fun findArenaByNameOrNull(
    name: String
  ): Pair<RawArena, ValidationResult>? {
    return this.findBy {
      ArenaTable.name.upperCase() eq name.uppercase(Locale.US)
    }
  }

  /**
   * @param predicate
   *
   * @since 0.1.0
   */
  private fun existsArenaBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Boolean {
    return !ArenaTable
      .select(ArenaTable.identifier)
      .where(predicate)
      .empty()
  }

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  private fun existsArenaMonumentPosition(
    identifier: UUID
  ): Boolean {
    return !ArenaMonumentPositionsTable
      .select(ArenaMonumentPositionsTable.monumentPositionIdentifier)
      .where {
        ArenaMonumentPositionsTable.monumentPositionIdentifier eq identifier
      }
      .empty()
  }

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  private fun existsArenaRevivalPosition(
    identifier: UUID
  ): Boolean {
    return !ArenaRevivalPositionsTable
      .select(ArenaRevivalPositionsTable.revivalPositionIdentifier)
      .where {
        ArenaRevivalPositionsTable.revivalPositionIdentifier eq identifier
      }
      .empty()
  }

  override suspend fun containsArenaByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      this@ArenaDao.existsArenaBy {
        ArenaTable.identifier eq identifier
      }
    }
  }

  override suspend fun containsArenaByName(
    name: String
  ): Boolean {
    return concurrentTransaction {
      this@ArenaDao.existsArenaBy {
        ArenaTable.name.upperCase() eq name.uppercase(Locale.US)
      }
    }
  }

  /**
   * @param arena
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    arena: RawArena,
    builder: UpdateBuilder<*>
  ) {
    ArenaTable.run {
      builder[this.name] = arena.name
    }
  }

  /**
   * @param arena
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    arena: RawArena,
    builder: UpdateBuilder<*>
  ) {
    builder[ArenaTable.identifier] = arena.identifier
    this.fillUpdateStatement(arena, builder)
  }

  override suspend fun insertArena(
    arena: RawArena?
  ): Pair<RawArena?, ValidationResult> {
    val result: ValidationResult = validateArena(arena, false)
    if (!result.isValid || null == arena) {
      return failure(result)
    }

    return concurrentTransaction {
      if (this@ArenaDao.existsArenaBy {
          (ArenaTable.identifier eq arena.identifier) or
            (ArenaTable.name.upperCase() eq arena.name.uppercase(Locale.US))
        }
      ) return@concurrentTransaction failure(
        "${EntityFieldNames.IDENTIFIER} or ${EntityFieldNames.NAME}",
        "An arena with such an identifier or name already exists in the dataset.",
        ResponseCodes.DUPLICATED,
        Pair(arena.identifier, arena.name)
      )

      val map: Pair<RawArenaMap?, ValidationResult> =
        ArenaMapDao.insertArenaMapWithoutValidation(arena.map)
      val mapResult: ValidationResult = map.second

      if (mapResult is BasicValidationResult.Failure
        && mapResult.hasErrorByCode(ResponseCodes.DUPLICATED)
        && (null == map.first || !mapResult.isValid)
      ) return@concurrentTransaction failure(mapResult)

      ArenaTable
        .insertReturning { statement: InsertStatement<*> ->
          this@ArenaDao.fillInsertStatement(arena, statement)
        }
        .firstNotNullOf { row: ResultRow ->
          mapToRawArena(row, arenaMap = map)
        }
    }
  }

  override suspend fun insertArenaMonumentPosition(
    identifier: UUID?,
    monumentPosition: RawMonumentPosition?
  ): Pair<Int?, ValidationResult> {
    var result: ValidationResult = validateUuid(identifier)
    if (!result.isValid || null == identifier) return failure(result)

    result = validateMonumentPosition(monumentPosition, false)
    if (!result.isValid || null == monumentPosition) return failure(result)

    return concurrentTransaction {
      if (!this@ArenaDao.existsArenaBy { ArenaTable.identifier eq identifier })
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "An arena with such an identifier does not exist in the dataset.",
          ResponseCodes.DUPLICATED,
          identifier
        )

      if (!MonumentPositionDao.exists(monumentPosition.identifier))
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "The position of a monument with such an identifier does not exist in the dataset.",
          ResponseCodes.DUPLICATED,
          monumentPosition.identifier
        )

      if (this@ArenaDao.existsArenaMonumentPosition(monumentPosition.identifier))
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "The position of a monument with such an identifier is already assigned to an arena with such an identifier.",
          ResponseCodes.DUPLICATED,
          monumentPosition.identifier
        )

      success(
        ArenaMonumentPositionsTable
          .insert { statement: InsertStatement<*> ->
            statement[this.arenaIdentifier] = identifier
            statement[this.monumentPositionIdentifier] = monumentPosition.identifier
          }
          .insertedCount
      )
    }
  }

  override suspend fun insertArenaRevivalPosition(
    identifier: UUID?,
    revivalPosition: RawRevivalPosition?
  ): Pair<Int?, ValidationResult> {
    var result: ValidationResult = validateUuid(identifier)
    if (!result.isValid || null == identifier) return failure(result)

    result = validateRevivalPosition(revivalPosition, false)
    if (!result.isValid || null == revivalPosition) return failure(result)

    return concurrentTransaction {
      if (!this@ArenaDao.existsArenaBy { ArenaTable.identifier eq identifier })
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "An arena with such an identifier does not exist in the dataset.",
          ResponseCodes.DUPLICATED,
          identifier
        )

      if (!RevivalPositionDao.exists(revivalPosition.identifier))
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "The position of a monument with such an identifier does not exist in the dataset.",
          ResponseCodes.DUPLICATED,
          revivalPosition.identifier
        )

      if (this@ArenaDao.existsArenaRevivalPosition(revivalPosition.identifier))
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "The position of a monument with such an identifier does not exist in the dataset.",
          ResponseCodes.DUPLICATED,
          revivalPosition.identifier
        )

      success(
        ArenaRevivalPositionsTable
          .insert { statement: InsertStatement<*> ->
            statement[this.arenaIdentifier] = identifier
            statement[this.revivalPositionIdentifier] = revivalPosition.identifier
          }
          .insertedCount
      )
    }
  }

  override suspend fun updateArena(
    arena: RawArena?
  ): Int {
    TODO("Not yet implemented")
  }

  override suspend fun deleteArenaByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      ArenaTable.deleteWhere { _: ISqlExpressionBuilder ->
        ArenaTable.identifier eq identifier
      }
    }
  }

  override suspend fun deleteArenaMonumentPosition(
    identifier: UUID,
    monumentPositionIdentifier: UUID,
    deleteReference: Boolean
  ): Int {
    return concurrentTransaction {
      // The number of rows that were deleted.
      val rows: Int = ArenaMonumentPositionsTable.deleteWhere { _: ISqlExpressionBuilder ->
        (this.arenaIdentifier eq identifier) and
          (this.monumentPositionIdentifier eq monumentPositionIdentifier)
      }

      if (0 >= rows || !deleteReference) {
        return@concurrentTransaction rows
      }

      // The number of rows that were deleted in the table from the reference.
      val secondRows: Int = MonumentPositionDao.deleteMonumentPositionByIdentifier(
        monumentPositionIdentifier
      )

      secondRows + rows
    }
  }

  override suspend fun deleteArenaMonumentPositions(
    identifier: UUID,
    deleteReference: Boolean
  ): Int {
    TODO("Not yet implemented")
  }

  override suspend fun deleteArenaRevivalPosition(
    identifier: UUID,
    revivalPositionIdentifier: UUID,
    deleteReference: Boolean
  ): Int {
    return concurrentTransaction {
      // The number of rows that were deleted.
      val rows: Int = ArenaRevivalPositionsTable.deleteWhere { _: ISqlExpressionBuilder ->
        (this.arenaIdentifier eq identifier) and
          (this.revivalPositionIdentifier eq revivalPositionIdentifier)
      }

      if (0 >= rows || !deleteReference) {
        return@concurrentTransaction rows
      }

      // The number of rows that were deleted in the table from the reference.
      val secondRows: Int = MonumentPositionDao.deleteMonumentPositionByIdentifier(
        revivalPositionIdentifier
      )

      secondRows + rows
    }
  }

  override suspend fun deleteArenaRevivalPositions(
    identifier: UUID,
    deleteReference: Boolean
  ): Int {
    TODO("Not yet implemented")
  }

  override suspend fun truncateArenas(): Int {
    return concurrentTransaction {
      ArenaTable.deleteAll()
    }
  }

  override suspend fun countArenas(): Long {
    return concurrentTransaction(readOnly = true) {
      ArenaTable
        .select(ArenaTable.identifier)
        .count()
    }
  }

  override suspend fun countArenaMonumentPositionsByIdentifier(
    identifier: UUID,
    teamIdentifier: UUID?
  ): Long {
    return concurrentTransaction(readOnly = true) {
      if (null == teamIdentifier) {
        return@concurrentTransaction ArenaMonumentPositionsTable
          .select(ArenaMonumentPositionsTable.arenaIdentifier)
          .where {
            ArenaMonumentPositionsTable.arenaIdentifier eq identifier
          }
          .count()
      }

      if (!TeamDao.exists(teamIdentifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD.toLong()
      }

      return@concurrentTransaction ArenaMonumentPositionsTable
        .innerJoin(MonumentPositionTable)
        .select(emptyList())
        .where {
          (ArenaMonumentPositionsTable.arenaIdentifier eq identifier) and
            (MonumentPositionTable.teamIdentifier eq teamIdentifier)
        }
        .count()
    }
  }

  override suspend fun countArenaRevivalPositionsByIdentifier(
    identifier: UUID,
    teamIdentifier: UUID?
  ): Long {
    return concurrentTransaction(readOnly = true) {
      if (null == teamIdentifier) {
        return@concurrentTransaction ArenaRevivalPositionsTable
          .select(ArenaRevivalPositionsTable.arenaIdentifier)
          .where {
            ArenaRevivalPositionsTable.arenaIdentifier eq identifier
          }
          .count()
      }

      if (!TeamDao.exists(teamIdentifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD.toLong()
      }

      return@concurrentTransaction ArenaRevivalPositionsTable
        .innerJoin(RevivalPositionTable)
        .select(emptyList())
        .where {
          (ArenaRevivalPositionsTable.arenaIdentifier eq identifier) and
            (RevivalPositionTable.teamIdentifier eq teamIdentifier)
        }
        .count()
    }
  }
}