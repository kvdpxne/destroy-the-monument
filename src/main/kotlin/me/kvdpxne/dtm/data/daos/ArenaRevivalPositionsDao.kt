package me.kvdpxne.dtm.data.daos

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.data.repositories.ArenaRevivalPositionsRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.ArenaRevivalPositionsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.position.revival.RevivalPosition
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
object ArenaRevivalPositionsDao : ArenaRevivalPositionsRepository {

  override suspend fun findArenaRevivalPositionsByArenaIdentifier(
    identifier: UUID
  ): Flow<RevivalPosition<Team>> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaRevivalPositionsTable
        .select(ArenaRevivalPositionsTable.revivalPositionIdentifier)
        .where {
          ArenaRevivalPositionsTable.arenaIdentifier eq identifier
        }
        .asFlow()
        .mapNotNull { row: ResultRow ->
          RevivalPositionDao.findRevivalPositionByIdentifier(
            row[ArenaRevivalPositionsTable.revivalPositionIdentifier]
          )
        }
    }
  }

  override suspend fun insertArenaRevivalPosition(
    arena: Arena,
    revivalPosition: RevivalPosition<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaRevivalPositionsTable
        .insert { it: InsertStatement<Number> ->
          it[this.arenaIdentifier] = arena.identifier
          it[this.revivalPositionIdentifier] = revivalPosition.identifier
        }
    }
  }

  override suspend fun deleteArenaRevivalPosition(
    arena: Arena,
    revivalPosition: RevivalPosition<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaRevivalPositionsTable.deleteWhere { _: ISqlExpressionBuilder ->
        (this.arenaIdentifier eq arena.identifier) and
          (this.revivalPositionIdentifier eq revivalPosition.identifier)
      }
    }
  }

  override suspend fun deleteArenaRevivalPositions(
    arena: Arena
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaRevivalPositionsTable.deleteWhere { _: ISqlExpressionBuilder ->
        this.arenaIdentifier eq arena.identifier
      }
    }
  }
}