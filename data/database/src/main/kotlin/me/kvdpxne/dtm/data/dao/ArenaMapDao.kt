package me.kvdpxne.dtm.data.dao

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.toRawArenaMap
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.repositories.ArenaMapRepository
import me.kvdpxne.dtm.data.tables.ArenaMapTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateArenaMap
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.updateReturning
import org.jetbrains.exposed.sql.upperCase

object ArenaMapDao : ArenaMapRepository {

  override suspend fun readArenaMaps(): Collection<Pair<RawArenaMap, ValidationResult>> {
    return concurrentTransaction(readOnly = true) {
      ArenaMapTable
        .selectAll()
        .map(ResultRow::toRawArenaMap)
        .toList()
    }
  }

  override suspend fun readArenaMapIdentifiers(): Collection<UUID> {
    return concurrentTransaction(readOnly = true) {
      ArenaMapTable
        .select(ArenaMapTable.identifier)
        .map { row: ResultRow ->
          row[ArenaMapTable.identifier]
        }
        .toList()
    }
  }

  override suspend fun readArenaMapNames(): Collection<String> {
    return concurrentTransaction(readOnly = true) {
      ArenaMapTable
        .select(ArenaMapTable.name)
        .map { row: ResultRow ->
          row[ArenaMapTable.name].uppercase(Locale.US)
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
  ): Pair<RawArenaMap, ValidationResult>? {
    return concurrentTransaction(readOnly = true) {
      ArenaMapTable
        .selectAll()
        .where(predicate)
        .firstNotNullOfOrNull(ResultRow::toRawArenaMap)
    }
  }

  override suspend fun findArenaMapByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawArenaMap, ValidationResult>? {
    return this.findBy {
      ArenaMapTable.identifier eq identifier
    }
  }

  override suspend fun findArenaMapByNameOrNull(
    name: String
  ): Pair<RawArenaMap, ValidationResult>? {
    return this.findBy {
      ArenaMapTable.name.upperCase() eq name.uppercase(Locale.US)
    }
  }

  /**
   * @param predicate
   *
   * @since 0.1.0
   */
  private fun exists(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Boolean {
    return !ArenaMapTable
      .select(ArenaMapTable.identifier)
      .where(predicate)
      .empty()
  }

  override suspend fun containsArenaMapByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      this@ArenaMapDao.exists {
        ArenaMapTable.identifier eq identifier
      }
    }
  }

  override suspend fun containsArenaMapByName(
    name: String
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      this@ArenaMapDao.exists {
        ArenaMapTable.name.upperCase() eq name.uppercase(Locale.US)
      }
    }
  }

  /**
   * @param arenaMap
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    arenaMap: RawArenaMap,
    builder: UpdateBuilder<*>
  ) {
    ArenaMapTable.run {
      builder[this.name] = arenaMap.name
    }
  }

  /**
   * @param arenaMap
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    arenaMap: RawArenaMap,
    builder: UpdateBuilder<*>
  ) {
    builder[ArenaMapTable.identifier] = arenaMap.identifier
    this.fillUpdateStatement(arenaMap, builder)
  }

  /**
   * @param arenaMap
   *
   * @since 0.1.0
   */
  suspend fun insertArenaMapWithoutValidation(
    arenaMap: RawArenaMap?,
  ): Pair<RawArenaMap?, ValidationResult> {
    if (null == arenaMap) {
      return this.insertArenaMap(null)
    }
    return concurrentTransaction {
      if (this@ArenaMapDao.exists {
          (ArenaMapTable.identifier eq arenaMap.identifier) or
            (ArenaMapTable.name.upperCase() eq arenaMap.name.uppercase(Locale.US))
        }
      ) return@concurrentTransaction failure(
        "${EntityFieldNames.IDENTIFIER} or ${EntityFieldNames.NAME}",
        "",
        ResponseCodes.DUPLICATED,
        Pair(arenaMap.identifier, arenaMap.name)
      )

      ArenaMapTable
        .insertReturning { statement: InsertStatement<*> ->
          this@ArenaMapDao.fillInsertStatement(arenaMap, statement)
        }
        .firstNotNullOf(ResultRow::toRawArenaMap)
    }
  }

  override suspend fun insertArenaMap(
    arenaMap: RawArenaMap?
  ): Pair<RawArenaMap?, ValidationResult> {
    val result: ValidationResult = validateArenaMap(arenaMap)
    return if (!result.isValid) failure(result)
    else this.insertArenaMapWithoutValidation(arenaMap)
  }

  override suspend fun updateArenaMap(
    arenaMap: RawArenaMap?
  ): Pair<RawArenaMap?, ValidationResult> {
    val result: ValidationResult = validateArenaMap(arenaMap)
    if (!result.isValid || null == arenaMap) {
      return failure(result)
    }

    return concurrentTransaction {
      if (!this@ArenaMapDao.exists {
          ArenaMapTable.identifier eq arenaMap.identifier
        }) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "",
          ResponseCodes.DUPLICATED,
          arenaMap.identifier
        )
      }

      ArenaMapTable
        .updateReturning(where = {
          ArenaMapTable.identifier eq arenaMap.identifier
        }) { statement: UpdateStatement ->
          this@ArenaMapDao.fillUpdateStatement(arenaMap, statement)
        }
        .firstNotNullOf(ResultRow::toRawArenaMap)
    }
  }

  override suspend fun deleteArenaMapByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      ArenaMapTable.deleteWhere { _: ISqlExpressionBuilder ->
        this.identifier eq identifier
      }
    }
  }

  override suspend fun truncateArenaMaps(): Int {
    return concurrentTransaction {
      ArenaMapTable.deleteAll()
    }
  }

  override suspend fun countArenaMaps(): Long {
    return concurrentTransaction(readOnly = true) {
      ArenaMapTable
        .select(ArenaMapTable.identifier)
        .count()
    }
  }
}