package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.ArenaMonumentPositionsRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.ArenaMonumentPositionsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.Team
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert

/**
 * @since 0.1.0
 */
object ArenaMonumentPositionsDao : ArenaMonumentPositionsRepository {

  /**
   * @since 0.1.0
   */
  override suspend fun findArenaMonumentPositionsByArenaIdentifier(
    identifier: UUID
  ): List<MonumentPosition<Team>> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaMonumentPositionsTable
        .select(ArenaMonumentPositionsTable.monumentPositionIdentifier)
        .where {
          ArenaMonumentPositionsTable.arenaIdentifier eq identifier
        }
        .mapNotNull { row: ResultRow ->
          MonumentPositionDao.findMonumentPositionByIdentifier(
            row[ArenaMonumentPositionsTable.monumentPositionIdentifier]
          )
        }
        .toList()
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun insertArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPosition<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaMonumentPositionsTable
        .insert {
          it[this.arenaIdentifier] = arena.identifier
          it[this.monumentPositionIdentifier] = monumentPosition.identifier
        }
    }
  }

  /**
   * @since 0.1.0
   */
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

  /**
   * @since 0.1.0
   */
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