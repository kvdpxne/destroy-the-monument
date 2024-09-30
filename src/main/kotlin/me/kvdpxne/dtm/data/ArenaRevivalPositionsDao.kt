package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.ArenaRevivalPositionsRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.ArenaRevivalPositionsTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.RevivalPosition
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
object ArenaRevivalPositionsDao : ArenaRevivalPositionsRepository {

  /**
   * @since 0.1.0
   */
  override suspend fun findArenaRevivalPositionsByArenaIdentifier(
    identifier: UUID
  ): List<RevivalPosition<Team>> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaRevivalPositionsTable
        .select(ArenaRevivalPositionsTable.revivalPositionIdentifier)
        .where {
          ArenaRevivalPositionsTable.arenaIdentifier eq identifier
        }
        .mapNotNull { row: ResultRow ->
          RevivalPositionDao.findRevivalPositionByIdentifier(
            row[ArenaRevivalPositionsTable.revivalPositionIdentifier]
          )
        }
        .toList()
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun insertArenaRevivalPosition(
    arena: Arena,
    revivalPosition: RevivalPosition<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaRevivalPositionsTable
        .insert {
          it[this.arenaIdentifier] = arena.identifier
          it[this.revivalPositionIdentifier] = revivalPosition.identifier
        }
    }
  }

  /**
   * @since 0.1.0
   */
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

  /**
   * @since 0.1.0
   */
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