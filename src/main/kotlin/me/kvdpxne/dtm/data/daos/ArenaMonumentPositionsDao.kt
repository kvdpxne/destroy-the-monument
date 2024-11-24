package me.kvdpxne.dtm.data.daos

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.data.repositories.ArenaMonumentPositionsRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.ArenaMonumentPositionsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.position.monument.MonumentPosition
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
object ArenaMonumentPositionsDao : ArenaMonumentPositionsRepository {

  override suspend fun findArenaMonumentPositionsByArenaIdentifier(
    identifier: UUID
  ): Flow<MonumentPosition<Team>> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaMonumentPositionsTable
        .select(ArenaMonumentPositionsTable.monumentPositionIdentifier)
        .where {
          ArenaMonumentPositionsTable.arenaIdentifier eq identifier
        }
        .asFlow()
        .mapNotNull { row: ResultRow ->
          MonumentPositionDao.findMonumentPositionByIdentifier(
            row[ArenaMonumentPositionsTable.monumentPositionIdentifier]
          )
        }
    }
  }

  override suspend fun insertArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPosition<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaMonumentPositionsTable
        .insert { it: InsertStatement<Number> ->
          it[this.arenaIdentifier] = arena.identifier
          it[this.monumentPositionIdentifier] = monumentPosition.identifier
        }
    }
  }

  override suspend fun deleteArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPosition<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaMonumentPositionsTable.deleteWhere { _: ISqlExpressionBuilder ->
        (this.arenaIdentifier eq arena.identifier) and
          (this.monumentPositionIdentifier eq monumentPosition.identifier)
      }
    }
  }

  override suspend fun deleteArenaMonumentPositions(
    arena: Arena
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaMonumentPositionsTable.deleteWhere { _: ISqlExpressionBuilder ->
        this.arenaIdentifier eq arena.identifier
      }
    }
  }
}