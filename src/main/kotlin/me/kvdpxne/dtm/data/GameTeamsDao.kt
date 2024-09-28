package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.GameTeamsRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.GameTeamsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @since 0.1.0
 */
object GameTeamsDao : GameTeamsRepository {

  /**
   * @since 0.1.0
   */
  override suspend fun findGameTeamsByGameIdentifier(
    identifier: UUID
  ): List<Team> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTeamsTable
        .select(GameTeamsTable.teamIdentifier)
        .where {
          GameTeamsTable.gameIdentifier eq identifier
        }
        .mapNotNull { row: ResultRow ->
          TeamDao.findTeamByIdentifier(
            row[GameTeamsTable.teamIdentifier]
          )
        }
        .toList()
    }
  }

  override suspend fun insertGameTeam(
    game: Game<Team>,
    team: Team
  ) {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTeamsTable.insert {
        it[this.gameIdentifier] = game.identifier
        it[this.teamIdentifier] = team.identifier
      }
    }
  }
}