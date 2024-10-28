package me.kvdpxne.dtm.data

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull
import me.kvdpxne.dtm.data.repositories.GameArenasRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.GameArenasTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team
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
object GameArenasDao : GameArenasRepository {

  override suspend fun findGameArenasByGameIdentifier(
    identifier: UUID
  ): Flow<Arena> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameArenasTable
        .select(GameArenasTable.arenaIdentifier)
        .where {
          GameArenasTable.gameIdentifier eq identifier
        }
        .asFlow()
        .mapNotNull { row: ResultRow ->
          ArenaDao.findArenaByIdentifier(
            row[GameArenasTable.arenaIdentifier]
          )
        }
    }
  }

  override suspend fun insertGameArena(
    game: Game<Team>,
    arena: Arena
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      GameArenasTable.insert { it: InsertStatement<Number> ->
        it[this.gameIdentifier] = game.identifier
        it[this.arenaIdentifier] = arena.identifier
      }
    }
  }

  override suspend fun deleteGameArena(
    game: Game<Team>,
    arena: Arena
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      GameArenasTable.deleteWhere { _: ISqlExpressionBuilder ->
        (this.gameIdentifier eq game.identifier) and
          (this.arenaIdentifier eq arena.identifier)
      }
    }
  }

  override suspend fun deleteGameArenas(
    game: Game<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      GameArenasTable.deleteWhere { _: ISqlExpressionBuilder ->
        this.gameIdentifier eq game.identifier
      }
    }
  }
}