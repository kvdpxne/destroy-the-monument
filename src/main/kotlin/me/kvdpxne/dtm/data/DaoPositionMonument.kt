package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TablePositionMonument
import me.kvdpxne.dtm.data.tables.TableTeam
import me.kvdpxne.dtm.game.MonumentPositionImpl
import me.kvdpxne.dtm.game.TeamImpl
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.TeamColors
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
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
    val teamIdentifier = row[TablePositionMonument.teamIdentifier]!!
    val name = row[TableTeam.name]!!

    //
    val x = row[TablePositionMonument.x]!!
    val y = row[TablePositionMonument.y]!!
    val z = row[TablePositionMonument.z]!!

    //
    return MonumentPositionImpl(
      x,
      y,
      z,
      TeamImpl(
        name,
        TeamColors.findTeamColorByName(name)!!,
        teamIdentifier
      ) as T,
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
      .innerJoin(
        TableTeam,
        TablePositionMonument.teamIdentifier eq TableTeam.identifier
      )
      .select(
        TablePositionMonument.identifier,
        TablePositionMonument.x,
        TablePositionMonument.y,
        TablePositionMonument.z,
        TablePositionMonument.teamIdentifier,
        TableTeam.name
      )
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