package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TablePositionMonument
import me.kvdpxne.dtm.game.MonumentPosition
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
  private fun toPositionMonument(
    row: QueryRowSet
  ): MonumentPosition {
    //
    val identifier = row[TablePositionMonument.identifier]!!

    //
    val teamIdentityIdentifier = row[TablePositionMonument.teamIdentityIdentifier]!!
    val teamIdentity = DaoTeam.findTeamByIdentifier(teamIdentityIdentifier)!!

    //
    val x = row[TablePositionMonument.x]!!
    val y = row[TablePositionMonument.y]!!
    val z = row[TablePositionMonument.z]!!

    //
    return MonumentPosition(
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
  fun findPositionMonumentByIdentifierOrNull(
    identifier: String
  ): MonumentPosition? {
    return database.from(TablePositionMonument)
      .select()
      .where {
        //
        TablePositionMonument.identifier eq identifier
      }
      .map {
        toPositionMonument(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertPositionMonument(
    monument: MonumentPosition
  ) {
    database.insert(TablePositionMonument) {
      set(it.identifier, monument.identifier)
      set(it.teamIdentityIdentifier, monument.team.identifier)
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