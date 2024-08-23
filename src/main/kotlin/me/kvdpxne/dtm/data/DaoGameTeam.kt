package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableGameTeam
import me.kvdpxne.dtm.game.Team
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoGameTeam {

  /**
   * @since 0.1.0
   */
  fun findGameTeamByGameIdentifier(
    identifier: String
  ): List<Team> {
    return database.from(TableGameTeam)
      .select()
      .where {
        TableGameTeam.gameIdentifier eq identifier
      }
      .mapNotNull {
        DaoTeam.findTeamByIdentifier(
          it[TableGameTeam.teamIdentifier]!!
        )
      }
      .toList()
  }

  /**
   * @since 0.1.0
   */
  fun insertGameTeam(
    gameIdentifier: String,
    teamIdentifier: String
  ) {
    database.insert(TableGameTeam) {
      set(it.gameIdentifier, gameIdentifier)
      set(it.teamIdentifier, teamIdentifier)
    }
  }
}