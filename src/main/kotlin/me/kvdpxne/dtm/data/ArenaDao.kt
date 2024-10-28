package me.kvdpxne.dtm.data

import java.util.UUID
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.repositories.ArenaRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.ArenaTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaImpl
import me.kvdpxne.dtm.game.ArenaMap
import me.kvdpxne.dtm.game.ArenaMapImpl
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.game.Team
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.update

/**
 * @since 0.1.0
 */
object ArenaDao : ArenaRepository {

  /**
   * @since 0.1.0
   */
  private val FIELDS: List<Column<*>> = listOf(
    ArenaTable.identifier,
    ArenaTable.name,
    ArenaTable.mapIdentifier,
    ArenaTable.mapName
  )

  /**
   * @since 0.1.0
   */
  private fun ResultRow.toArena(): Arena {
    // Unikalny identyfikator obiektu "Arena".
    val identifier: UUID = this[ArenaTable.identifier]

    //
    val mapIdentifier: UUID? = this[ArenaTable.mapIdentifier]
    val mapName: String? = this[ArenaTable.mapName]

    // Unikalna nazwa obiektu "Game".
    val name: String = this[ArenaTable.name]

    //
    val arena = ArenaImpl(
      name,
      if (null != mapIdentifier && null != mapName) {
        ArenaMapImpl(
          mapName,
          mapIdentifier
        )
      } else {
        null
      },
      identifier
    )

    runBlocking {
      launch {
        ArenaMonumentPositionsDao
          .findArenaMonumentPositionsByArenaIdentifier(identifier)
          .collect { monumentPosition: MonumentPosition<Team> ->
            arena.addPositionMonument(monumentPosition)
          }
      }

      launch {
        ArenaRevivalPositionsDao
          .findArenaRevivalPositionsByArenaIdentifier(identifier)
          .collect { revivalPosition: RevivalPosition<Team> ->
            arena.addRevivalPosition(revivalPosition)
          }
      }
    }

    return arena
  }

  override suspend fun findArenas(): List<Arena> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaTable
        .select(FIELDS)
        .map { row: ResultRow ->
          row.toArena()
        }
        .toList()
    }
  }

  private suspend fun findArenaBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Arena? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaTable
        .select(FIELDS)
        .where(predicate)
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toArena()
        }
    }
  }

  override suspend fun findArenaByIdentifier(
    identifier: UUID
  ): Arena? {
    return this.findArenaBy {
      ArenaTable.identifier eq identifier
    }
  }

  override suspend fun findArenaByName(
    name: String,
    ignoreCase: Boolean
  ): Arena? {
    return this.findArenaBy {
      if (ignoreCase) {
        ArenaTable.name.lowerCase() eq name.lowercase()
      } else {
        ArenaTable.name eq name
      }
    }
  }

  override suspend fun insertArena(
    arena: Arena
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaTable.insert {
        it[this.identifier] = arena.identifier
        it[this.name] = arena.name
      }.insertedCount
    }
  }

  override suspend fun updateArena(
    arena: Arena
  ): Int {
    TODO("Not yet implemented")
  }

  override suspend fun updateArenaMap(arena: Arena, map: ArenaMap) {
    concurrentTransaction(DatabasesConfiguration.main) {
      ArenaTable.update({
        ArenaTable.identifier eq arena.identifier
      }) {
        it[this.mapIdentifier] = map.identifier
        it[this.mapName] = map.name
      }
    }
  }

  override suspend fun deleteArenaByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaTable.deleteWhere {
        this.identifier eq identifier
      }
    }
  }

  override suspend fun countArenas(): Long {
    return concurrentTransaction(DatabasesConfiguration.main) {
      ArenaTable.select(ArenaTable.identifier).count()
    }
  }
}