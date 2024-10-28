package me.kvdpxne.dtm.data

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull
import me.kvdpxne.dtm.data.repositories.GameTeamsRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.GameTeamsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.team.Team
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement

/**
 * @since 0.1.0
 */
object GameTeamsDao : GameTeamsRepository {

  override suspend fun findGameTeamsByGameIdentifier(
    identifier: UUID
  ): Flow<Team> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTeamsTable
        .select(GameTeamsTable.teamIdentifier)
        .where {
          GameTeamsTable.gameIdentifier eq identifier
        }
        .asFlow()
        .mapNotNull { row: ResultRow ->
          TeamDao.findTeamByIdentifier(
            row[GameTeamsTable.teamIdentifier]
          )
        }
    }
  }

  override suspend fun insertGameTeam(
    game: Game<Team>,
    team: Team
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      GameTeamsTable.insert { it: InsertStatement<Number> ->
        it[this.gameIdentifier] = game.identifier
        it[this.teamIdentifier] = team.identifier
      }
    }
  }

  override suspend fun deleteGameTeam(
    game: Game<Team>,
    team: Team
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      GameTeamsTable.deleteWhere { _: ISqlExpressionBuilder ->
        (this.gameIdentifier eq game.identifier) and
          (this.teamIdentifier eq team.identifier)
      }
    }
  }

  override suspend fun deleteGameTeams(
    game: Game<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      GameTeamsTable.deleteWhere { _: ISqlExpressionBuilder ->
        this.gameIdentifier eq game.identifier
      }
    }
  }
}