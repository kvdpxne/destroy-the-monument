package me.kvdpxne.dtm.data

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.repositories.GameRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.GameTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameImpl
import me.kvdpxne.dtm.game.Team
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
object GameDao : GameRepository {

  /**
   * @since 0.1.0
   */
  private val FIELDS: List<Column<*>> = listOf(
    GameTable.identifier,
    GameTable.name
  )

  /**
   * @since 0.1.0
   */
  private fun ResultRow.toGame(): Game<Team> {
    // Unikalny identyfikator obiektu "Game".
    val identifier: UUID = this[GameTable.identifier]

    // Unikalna nazwa obiektu "Game".
    val name: String = this[GameTable.name]

    //
    val teams: MutableMap<UUID, Team> = ConcurrentHashMap(8)
    val arenas: MutableMap<UUID, Arena> = ConcurrentHashMap(32)

    runBlocking {
      launch {
        GameTeamsDao
          .findGameTeamsByGameIdentifier(identifier)
          .collect { team: Team ->
            teams[team.identifier] = team
          }
      }

      launch {
        GameArenasDao
          .findGameArenasByGameIdentifier(identifier)
          .collect { arena: Arena ->
            arenas[arena.identifier] = arena
          }
      }
    }

    return GameImpl(
      name.lowercase(),
      name,
      teams,
      arenas,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findGames(): List<Game<Team>> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTable
        .select(FIELDS)
        .map { row: ResultRow ->
          row.toGame()
        }
        .toList()
    }
  }

  private suspend fun findGameBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Game<Team>? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTable
        .select(FIELDS)
        .where(predicate)
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toGame()
        }
    }
  }

  override suspend fun findGameByIdentifier(
    identifier: UUID
  ): Game<Team>? {
    return this.findGameBy {
      GameTable.identifier eq identifier
    }
  }

  override suspend fun findGameByName(
    name: String,
    ignoreCase: Boolean
  ): Game<Team>? {
    return this.findGameBy {
      if (ignoreCase) {
        GameTable.name.lowerCase() eq name.lowercase()
      } else {
        GameTable.name eq name
      }
    }
  }

  /**
   * @param game
   * @param builder
   *
   * @since 0.1.0
   */
  private fun buildGameStatement(
    game: Game<Team>,
    builder: UpdateBuilder<Int>
  ) {
    GameTable.run {
      builder[this.name] = game.name
    }
  }

  override suspend fun insertGame(
    game: Game<Team>
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTable.insert {
        it[this.identifier] = game.identifier
        this@GameDao.buildGameStatement(game, it)
      }.insertedCount
    }
  }

  override suspend fun updateGame(
    game: Game<Team>
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTable.update({
        GameTable.identifier eq game.identifier
      }) {
        this@GameDao.buildGameStatement(game, it)
      }
    }
  }

  override suspend fun deleteGameByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTable.deleteWhere {
        this.identifier eq identifier
      }
    }
  }

  override suspend fun countGames(): Long {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTable.select(GameTable.identifier).count()
    }
  }
}