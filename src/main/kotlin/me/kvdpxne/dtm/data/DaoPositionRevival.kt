package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TablePositionRevival
import me.kvdpxne.dtm.game.BaseRevivalPosition
import me.kvdpxne.dtm.game.RevivalPosition
import me.kvdpxne.dtm.game.Team
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoPositionRevival {

  /**
   * @since 0.1.0
   */
  private fun <T : Team> toPositionRevival(
    row: QueryRowSet
  ): RevivalPosition<T> {
    //
    val identifier = row[TablePositionRevival.identifier]!!

    //
    val teamIdentifier = row[TablePositionRevival.teamIdentifier]!!
    val teamIdentity = DaoTeam.findTeamByIdentifier(teamIdentifier)!! as T

    //
    val x = row[TablePositionRevival.x]!!
    val y = row[TablePositionRevival.y]!!
    val z = row[TablePositionRevival.z]!!
    val pitch = row[TablePositionRevival.pitch]!!
    val yaw = row[TablePositionRevival.yaw]!!

    //
    return BaseRevivalPosition(
      x,
      y,
      z,
      pitch,
      yaw,
      teamIdentity,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun <T : Team> findPositionRevivalByIdentifierOrNull(
    identifier: String
  ): RevivalPosition<T>? {
    return database.from(TablePositionRevival)
      .select()
      .where {
        TablePositionRevival.identifier eq identifier
      }
      .map {
        this.toPositionRevival<T>(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertPositionRevival(
    revivalPosition: RevivalPosition<*>
  ) {
    database.insert(TablePositionRevival) {
      set(it.identifier, revivalPosition.identifier)
      set(it.teamIdentifier, revivalPosition.team.identifier)
      set(it.x, revivalPosition.x)
      set(it.y, revivalPosition.y)
      set(it.z, revivalPosition.z)
      set(it.pitch, revivalPosition.pitch)
      set(it.yaw, revivalPosition.yaw)
    }
  }

  /**
   * @since 0.1.0
   */
  fun deletePositionRevivalByIdentifier(
    identifier: String
  ) {
    database.delete(TablePositionRevival) {
      it.identifier eq identifier
    }
  }
}