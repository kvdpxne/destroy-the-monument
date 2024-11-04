package me.kvdpxne.dtm.data.daos

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.MonumentPositionRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.MonumentPositionTable
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.position.MonumentPosition
import me.kvdpxne.dtm.position.MonumentPositionImpl
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.TeamColors
import me.kvdpxne.dtm.team.TeamImpl
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update

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
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toMonumentPosition()
        }
    }
  }

  /**
   * @param monumentPosition
   * @param builder
   *
   * @since 0.1.0
   */
  private fun buildMonumentPositionStatement(
    monumentPosition: MonumentPosition<Team>,
    builder: UpdateBuilder<Int>
  ) {
    MonumentPositionTable.run {
      builder[this.teamIdentifier] = monumentPosition.team.identifier
      builder[this.x] = monumentPosition.x
      builder[this.y] = monumentPosition.y
      builder[this.z] = monumentPosition.z
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun insertMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      MonumentPositionTable.insert {
        it[this.identifier] = monumentPosition.identifier
        buildMonumentPositionStatement(monumentPosition, it)
      }.insertedCount
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun updateMonumentPosition(
    monumentPosition: MonumentPosition<Team>
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      MonumentPositionTable.update({
        MonumentPositionTable.identifier eq monumentPosition.identifier
      }) {
        buildMonumentPositionStatement(monumentPosition, it)
      }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun deleteMonumentPositionByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      MonumentPositionTable.deleteWhere {
        this.identifier eq identifier
      }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun countMonumentPositions(): Long {
    return concurrentTransaction(DatabasesConfiguration.main) {
      MonumentPositionTable
        .select(MonumentPositionTable.identifier)
        .count()
    }
  }
}