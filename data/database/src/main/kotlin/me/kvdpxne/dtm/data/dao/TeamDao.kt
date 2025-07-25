package me.kvdpxne.dtm.data.dao

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.toRawTeam
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.repositories.TeamRepository
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateTeam
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.updateReturning
import org.jetbrains.exposed.sql.upperCase

object TeamDao : TeamRepository {

  override suspend fun readTeams(): Collection<Pair<RawTeam, ValidationResult>> {
    return concurrentTransaction {
      TeamTable
        .selectAll()
        .map(ResultRow::toRawTeam)
        .toList()
    }
  }

  override suspend fun readTeamIdentifiers(): Collection<UUID> {
    return concurrentTransaction {
      TeamTable
        .select(TeamTable.identifier)
        .map { row: ResultRow ->
          row[TeamTable.identifier]
        }
        .toList()
    }
  }

  override suspend fun readTeamNames(): Collection<String> {
    return concurrentTransaction {
      TeamTable
        .select(TeamTable.name)
        .map { row: ResultRow ->
          row[TeamTable.name].uppercase(Locale.US)
        }
        .toList()
    }
  }

  /**
   * @param predicate
   * @since 0.1.0
   */
  private suspend fun findBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Pair<RawTeam, ValidationResult>? {
    return concurrentTransaction {
      TeamTable
        .selectAll()
        .where(predicate)
        .firstNotNullOfOrNull(ResultRow::toRawTeam)
    }
  }

  override suspend fun findTeamByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawTeam, ValidationResult>? {
    return this.findBy {
      TeamTable.identifier eq identifier
    }
  }

  override suspend fun findTeamByNameOrNull(
    name: String
  ): Pair<RawTeam, ValidationResult>? {
    return findBy {
      TeamTable.name.upperCase() eq name.uppercase(Locale.US)
    }
  }

  /**
   * @since 0.1.0
   */
  fun exists(
    identifier: UUID
  ): Boolean {
    return !TeamTable
      .select(TeamTable.identifier)
      .where {
        TeamTable.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsTeamByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      this@TeamDao.exists(identifier)
    }
  }

  override suspend fun containsTeamByName(
    name: String
  ): Boolean {
    return concurrentTransaction {
      !TeamTable
        .select(TeamTable.name)
        .where {
          TeamTable.name.upperCase() eq name.uppercase(Locale.US)
        }
        .empty()
    }
  }

  /**
   * @param team
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    team: RawTeam,
    builder: UpdateBuilder<*>
  ) {
    TeamTable.run {
      builder[this.name] = team.name
      builder[this.colorOfArmor] = team.colorOfArmor
      builder[this.colorOfProfession] = team.colorOfProfession
      builder[this.colorOnChat] = team.colorOnChat
      builder[this.colorOnPlayerList] = team.colorOnPlayerList
    }
  }

  /**
   * @param team
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    team: RawTeam,
    builder: UpdateBuilder<Int>
  ) {
    builder[TeamTable.identifier] = team.identifier
    this.fillUpdateStatement(team, builder)
  }

  suspend fun insertTeamWithoutValidation(
    team: RawTeam,
  ): Pair<RawTeam?, ValidationResult> {
    return concurrentTransaction {
      if (this@TeamDao.exists(team.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "",
          ResponseCodes.DUPLICATED,
          team.identifier
        )
      }

      TeamTable
        .insertReturning { statement: InsertStatement<*> ->
          this@TeamDao.fillInsertStatement(team, statement)
        }
        .firstNotNullOf(ResultRow::toRawTeam)
    }
  }

  override suspend fun insertTeam(
    team: RawTeam?
  ): Pair<RawTeam?, ValidationResult> {
    val result: ValidationResult = validateTeam(team)
    return if (!result.isValid || null == team) failure(result)
    else this.insertTeamWithoutValidation(team)
  }

  override suspend fun updateTeam(
    team: RawTeam?
  ): Pair<RawTeam?, ValidationResult> {
    val result: ValidationResult = validateTeam(team)
    if (!result.isValid || null == team) {
      return failure(result)
    }

    return concurrentTransaction {
      if (!this@TeamDao.exists(team.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "",
          ResponseCodes.DUPLICATED,
          team.identifier
        )
      }

      TeamTable
        .updateReturning(where = {
          TeamTable.identifier eq team.identifier
        }) { statement: UpdateStatement ->
          this@TeamDao.fillUpdateStatement(team, statement)
        }
        .firstNotNullOf(ResultRow::toRawTeam)
    }
  }

  override suspend fun deleteTeamByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      TeamTable.deleteWhere { _: ISqlExpressionBuilder ->
        TeamTable.identifier eq identifier
      }
    }
  }

  override suspend fun truncateTeams(): Int {
    return concurrentTransaction {
      TeamTable.deleteAll()
    }
  }

  override suspend fun countTeams(): Long {
    return concurrentTransaction {
      TeamTable
        .select(TeamTable.identifier)
        .count()
    }
  }
}