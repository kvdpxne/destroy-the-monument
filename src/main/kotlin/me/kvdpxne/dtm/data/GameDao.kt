package me.kvdpxne.dtm.data

import java.util.UUID
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
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.transaction

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
    val teams: MutableMap<UUID, Team> = mutableMapOf()

    //
    val arenas: MutableMap<UUID, Arena> = mutableMapOf()

    runBlocking {
      GameTeamsDao
        .findGameTeamsByGameIdentifier(identifier)
        .forEach { team: Team ->
          teams[team.identifier] = team
        }

      GameArenasDao
        .findGameArenasByGameIdentifier(identifier)
        .forEach { arena: Arena ->
          arenas[arena.identifier] = arena
        }

      null
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

  override suspend fun insertGame(
    game: Game<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      GameTable.insert {
        it[this.identifier] = game.identifier
        it[this.name] = game.name
      }
    }
  }

  override suspend fun countGames(): Long {
    return concurrentTransaction(DatabasesConfiguration.main) {
      GameTable.select(GameTable.identifier).count()
    }
  }
}