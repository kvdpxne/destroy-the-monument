package me.kvdpxne.dtm.data.daos

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.TeamRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.TeamColors
import me.kvdpxne.dtm.team.TeamImpl
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update

/**
 * @since 0.1.0
 */
object TeamDao : TeamRepository {

  /**
   * @since 0.1.0
   */
  private val FIELDS: List<Column<*>> = listOf(
    TeamTable.identifier,
    TeamTable.name
  )

  /**
   * @since 0.1.0
   */
  private fun ResultRow.toTeam(): Team {
    //
    val identifier: UUID = this[TeamTable.identifier]

    //
    val name: String = this[TeamTable.name]

    //
    return TeamImpl(
      name,
      TeamColors.findTeamColorByName(name)!!,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findTeams(): List<Team> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TeamTable
        .select(FIELDS)
        .map { row: ResultRow ->
          row.toTeam()
        }
        .toList()
    }
  }

  private suspend fun findTeamBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Team? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TeamTable
        .select(FIELDS)
        .where(predicate)
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toTeam()
        }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findTeamByIdentifier(
    identifier: UUID
  ): Team? {
    return findTeamBy {
      TeamTable.identifier eq identifier
    }
  }

  override suspend fun findTeamByName(
    name: String,
    ignoreCase: Boolean
  ): Team? {
    return findTeamBy {
      if (ignoreCase) {
        TeamTable.name.lowerCase() eq name.lowercase()
      } else {
        TeamTable.name eq name
      }
    }
  }

  /**
   * @param team
   * @param builder
   *
   * @since 0.1.0
   */
  private fun buildTeamStatement(
    team: Team,
    builder: UpdateBuilder<Int>
  ) {
    TeamTable.run {
      builder[this.name] = team.name
    }
  }

  override suspend fun insertTeam(
    team: Team
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TeamTable.insert {
        it[this.identifier] = team.identifier
        buildTeamStatement(team, it)
      }.insertedCount
    }
  }

  override suspend fun updateTeam(
    team: Team
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TeamTable.update({
        TeamTable.identifier eq team.identifier
      }) {
        buildTeamStatement(team, it)
      }
    }
  }

  override suspend fun deleteTeamByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TeamTable.deleteWhere {
        this.identifier eq identifier
      }
    }
  }
}