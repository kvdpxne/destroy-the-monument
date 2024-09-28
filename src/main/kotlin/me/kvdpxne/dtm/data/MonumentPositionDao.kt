package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.MonumentPositionRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.MonumentPositionTable
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.MonumentPositionImpl
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.TeamColors
import me.kvdpxne.dtm.game.TeamImpl
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @since 0.1.0
 */
object MonumentPositionDao : MonumentPositionRepository {

  /**
   * @since 0.1.0
   */
  private val FIELDS: List<Column<*>> = listOf(
    MonumentPositionTable.identifier,
    MonumentPositionTable.teamIdentifier,
    MonumentPositionTable.x,
    MonumentPositionTable.y,
    MonumentPositionTable.z,
    TeamTable.name
  )

  /**
   * @since 0.1.0
   */
  private fun ResultRow.toMonumentPosition(): MonumentPosition<Team> {
    //
    val identifier: UUID = this[MonumentPositionTable.identifier]

    //
    val teamIdentifier: UUID = this[MonumentPositionTable.teamIdentifier]

    //
    val name: String = this[TeamTable.name]

    //
    val x: Int = this[MonumentPositionTable.x]
    val y: Int = this[MonumentPositionTable.y]
    val z: Int = this[MonumentPositionTable.z]

    return MonumentPositionImpl(
      x,
      y,
      z,
      TeamImpl(
        name,
        TeamColors.findTeamColorByName(name)!!,
        teamIdentifier
      ),
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findMonumentPositions(): List<MonumentPosition<Team>> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      MonumentPositionTable
        .innerJoin(TeamTable)
        .select(FIELDS)
        .map { row: ResultRow ->
          row.toMonumentPosition()
        }
        .toList()
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findMonumentPositionByIdentifier(
    identifier: UUID
  ): MonumentPosition<Team>? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      MonumentPositionTable
        .innerJoin(TeamTable)
        .select(FIELDS)
        .where {
          MonumentPositionTable.identifier eq identifier
        }
        .firstOrNull()
        ?.let { row: ResultRow ->
          row.toMonumentPosition()
        }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun insertMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  ) {
    concurrentTransaction(DatabasesConfiguration.main) {
      MonumentPositionTable.insert {
        it[this.identifier] = monumentPosition.identifier
        it[this.teamIdentifier] = monumentPosition.team.identifier
        it[this.x] = monumentPosition.x
        it[this.y] = monumentPosition.y
        it[this.z] = monumentPosition.z
      }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun updateMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  ) {
    TODO("Not yet implemented")
  }

  /**
   * @since 0.1.0
   */
  override suspend fun deleteMonumentPositionByIdentifier(
    identifier: UUID
  ) {
    TODO("Not yet implemented")
  }
}