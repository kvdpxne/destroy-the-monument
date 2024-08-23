package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TablePositionMonument
import me.kvdpxne.dtm.game.BaseMonumentPosition
import me.kvdpxne.dtm.game.MonumentPosition
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
object DaoPositionMonument {

  /**
   * @since 0.1.0
   */
  private fun <T : Team> toPositionMonument(
    row: QueryRowSet
  ): MonumentPosition<T> {
    //
    val identifier = row[TablePositionMonument.identifier]!!

    //
    val teamIdentityIdentifier = row[TablePositionMonument.teamIdentifier]!!
    val teamIdentity = DaoTeam.findTeamByIdentifier(teamIdentityIdentifier)!! as T

    //
    val x = row[TablePositionMonument.x]!!
    val y = row[TablePositionMonument.y]!!
    val z = row[TablePositionMonument.z]!!

    //
    return BaseMonumentPosition(
      x,
      y,
      z,
      teamIdentity,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun <T : Team> findPositionMonumentByIdentifierOrNull(
    identifier: String
  ): MonumentPosition<T>? {
    return database.from(TablePositionMonument)
      .select()
      .where {
        //
        TablePositionMonument.identifier eq identifier
      }
      .map {
        this.toPositionMonument<T>(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertPositionMonument(
    monument: MonumentPosition<*>
  ) {
    database.insert(TablePositionMonument) {
      set(it.identifier, monument.identifier)
      set(it.teamIdentifier, monument.team.identifier)
      set(it.x, monument.x)
      set(it.y, monument.y)
      set(it.z, monument.z)
    }
  }

//  /**
//   * @since 0.1.0
//   */
//  fun updatePositionMonument(
//    monument: Monument
//  ) {
//    database.update(TablePositionMonument) {
//      set(it.teamIdentityIdentifier, monument.team.identifier)
//      set(it.x, monument.x)
//      set(it.y, monument.y)
//      set(it.z, monument.z)
//
//      where {
//        TablePositionMonument.identifier eq monument.identifier
//      }
//    }
//  }

  /**
   * @since 0.1.0
   */
  fun deletePositionMonumentByIdentifier(
    identifier: String
  ) {
    database.delete(TablePositionMonument) {
      it.identifier eq identifier
    }
  }
}