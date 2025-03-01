package me.kvdpxne.dtm.data.dao

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.extensions.mapping.toTeam
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.repositories.RepositoryTeam
import me.kvdpxne.dtm.data.tables.TableTeam
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.context.extensions.validate
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

/**
 * @since 0.1.0
 */
object DaoTeam : RepositoryTeam {

  /**
   * @since 0.1.0
   */
  internal val COLUMNS: Array<Column<*>> by lazy {
    arrayOf(
      TableTeam.name,
      TableTeam.colorOfArmor,
      TableTeam.colorOfProfession,
      TableTeam.colorOnChat,
      TableTeam.colorOnPlayerList
    )
  }

  override suspend fun readTeams(): Collection<RawTeam> {
    return concurrentTransaction {
      TableTeam
        .selectAll()
        .map { row: ResultRow ->
          row.toTeam()
        }
        .toList()
    }
  }

  override suspend fun readTeamIdentifiers(): Collection<UUID> {
    return concurrentTransaction {
      TableTeam
        .select(TableTeam.identifier)
        .map { row: ResultRow ->
          row[TableTeam.identifier]
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
  ): RawTeam? {
    return concurrentTransaction {
      TableTeam
        .selectAll()
        .where(predicate)
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toTeam()
        }
    }
  }

  override suspend fun findTeamByIdentifierOrNull(
    identifier: UUID
  ): RawTeam? {
    return findBy {
      TableTeam.identifier eq identifier
    }
  }

  override suspend fun findTeamByNameOrNull(
    name: String
  ): RawTeam? {
    return findBy {
      TableTeam.name.lowerCase() eq name.lowercase(Locale.US)
    }
  }

  /**
   * @since 0.1.0
   */
  internal fun exists(
    identifier: UUID
  ): Boolean {
    return !TableTeam
      .select(TableTeam.identifier)
      .where {
        TableTeam.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsTeamByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      this@DaoTeam.exists(identifier)
    }
  }

  override suspend fun containsTeamByName(
    name: String
  ): Boolean {
    return concurrentTransaction {
      !TableTeam
        .select(TableTeam.name)
        .where {
          TableTeam.name.lowerCase() eq name.lowercase()
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
    TableTeam.run {
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
    builder[TableTeam.identifier] = team.identifier
    this.fillUpdateStatement(team, builder)
  }

  override suspend fun insertTeam(
    team: RawTeam?
  ): Int {
    val result: Int = team.validate()
    if (EVERYTHING_OK != result || null == team) {
      return result
    }

    return concurrentTransaction {
      // A new record cannot be inserted into a database table if a previously
      // inserted record has the same identifier as the record to be inserted.
      if (exists(team.identifier)) {
        return@concurrentTransaction ResponseCodes.DUPLICATED
      }

      TableTeam.insert { statement: InsertStatement<*> ->
        fillInsertStatement(
          team,
          statement
        )
      }.insertedCount
    }
  }

  override suspend fun updateTeam(
    team: RawTeam?
  ): Int {
    val result: Int = team.validate()
    if (EVERYTHING_OK != result || null == team) {
      return result
    }

    return concurrentTransaction {
      if (!this@DaoTeam.exists(team.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD
      }

      TableTeam.update({
        TableTeam.identifier eq team.identifier
      }) { statement: UpdateStatement ->
        fillUpdateStatement(
          team,
          statement
        )
      }
    }
  }

  override suspend fun deleteTeamByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      TableTeam.deleteWhere { _: ISqlExpressionBuilder ->
        TableTeam.identifier eq identifier
      }
    }
  }

  override suspend fun truncateTeams(): Int {
    return concurrentTransaction {
      TableTeam.deleteAll()
    }
  }

  override suspend fun countTeams(): Long {
    return concurrentTransaction {
      TableTeam.selectAll().count()
    }
  }
}