package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.GameArenasRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.GameArenasTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert

/**
 * @since 0.1.0
 */
object GameArenasDao : GameArenasRepository {

  /**
   * @since 0.1.0
   */
  override suspend fun findGameArenasByGameIdentifier(
    identifier: UUID
  ): List<Arena> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameArenasTable
        .select(GameArenasTable.arenaIdentifier)
        .where {
          GameArenasTable.gameIdentifier eq identifier
        }
        .mapNotNull { row: ResultRow ->
          ArenaDao.findArenaByIdentifier(
            row[GameArenasTable.arenaIdentifier]
          )
        }
        .toList()
    }
  }

  override suspend fun insertGameArena(
    game: Game<Team>,
    arena: Arena
  ) {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameArenasTable.insert {
        it[this.gameIdentifier] = game.identifier
        it[this.arenaIdentifier] = arena.identifier
      }
    }
  }

  override suspend fun deleteGameArena(
    game: Game<Team>,
    arena: Arena
  ) {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameArenasTable.deleteWhere {
        (this.gameIdentifier eq game.identifier) and
          (this.arenaIdentifier eq arena.identifier)
      }
    }
  }
}