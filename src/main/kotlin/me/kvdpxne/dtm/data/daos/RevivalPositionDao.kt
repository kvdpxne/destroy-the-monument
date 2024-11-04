package me.kvdpxne.dtm.data.daos

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.RevivalPositionRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.RevivalPositionTable
import me.kvdpxne.dtm.data.tables.TeamTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.position.RevivalPositionImpl
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.TeamColors
import me.kvdpxne.dtm.team.TeamImpl
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update

/**
 * @since 0.1.0
 */
object RevivalPositionDao : RevivalPositionRepository {

  /**
   * @since 0.1.0
   */
  private val FIELDS: List<Column<*>> = listOf(
    RevivalPositionTable.identifier,
    RevivalPositionTable.teamIdentifier,
    RevivalPositionTable.x,
    RevivalPositionTable.y,
    RevivalPositionTable.z,
    RevivalPositionTable.pitch,
    RevivalPositionTable.yaw,
    TeamTable.name
  )

  /**
   * @since 0.1.0
   */
  private fun ResultRow.toRevivalPosition(): RevivalPosition<Team> {
    //
    val identifier: UUID = this[RevivalPositionTable.identifier]

    //
    val teamIdentifier: UUID = this[RevivalPositionTable.teamIdentifier]

    //
    val name: String = this[TeamTable.name]

    //
    val x: Double = this[RevivalPositionTable.x]
    val y: Double = this[RevivalPositionTable.y]
    val z: Double = this[RevivalPositionTable.z]
    val pitch: Float = this[RevivalPositionTable.pitch]
    val yaw: Float = this[RevivalPositionTable.yaw]

    return RevivalPositionImpl(
      x,
      y,
      z,
      pitch,
      yaw,
      TeamImpl(
        name,
        TeamColors.findTeamColorByName(name)!!,
        teamIdentifier,
      ),
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findRevivalPositions(): List<RevivalPosition<Team>> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      RevivalPositionTable
        .innerJoin(TeamTable)
        .select(FIELDS)
        .map { row: ResultRow ->
          row.toRevivalPosition()
        }
        .toList()
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findRevivalPositionByIdentifier(
    identifier: UUID
  ): RevivalPosition<Team>? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      RevivalPositionTable
        .innerJoin(TeamTable)
        .select(FIELDS)
        .where {
          RevivalPositionTable.identifier eq identifier
        }
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toRevivalPosition()
        }
    }
  }

  /**
   * @param revivalPosition
   * @param builder
   *
   * @since 0.1.0
   */
  private fun buildMonumentPositionStatement(
    revivalPosition: RevivalPosition<Team>,
    builder: UpdateBuilder<Int>
  ) {
    RevivalPositionTable.run {
      builder[this.teamIdentifier] = revivalPosition.team.identifier
      builder[this.x] = revivalPosition.x
      builder[this.y] = revivalPosition.y
      builder[this.z] = revivalPosition.z
      builder[this.pitch] = revivalPosition.pitch
      builder[this.yaw] = revivalPosition.yaw
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun insertRevivalPosition(
    revivalPosition: RevivalPosition<Team>
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      RevivalPositionTable.insert { it: InsertStatement<Number> ->
        it[this.identifier] = revivalPosition.identifier
        buildMonumentPositionStatement(revivalPosition, it)
      }.insertedCount
    }
  }

  override suspend fun updateRevivalPosition(
    revivalPosition: RevivalPosition<Team>
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      RevivalPositionTable.update({
        RevivalPositionTable.identifier eq revivalPosition.identifier
      }) {
        buildMonumentPositionStatement(revivalPosition, it)
      }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun deleteRevivalPositionByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      RevivalPositionTable.deleteWhere { _: ISqlExpressionBuilder ->
        this.identifier eq identifier
      }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun countRevivalPositions(): Long {
    return concurrentTransaction(DatabasesConfiguration.main) {
      RevivalPositionTable
        .select(RevivalPositionTable.identifier)
        .count()
    }
  }
}