package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableTeam
import me.kvdpxne.dtm.game.BaseTeam
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.game.TeamColors
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoTeam {

  /**
   * @since 0.1.0
   */
  private fun toTeam(
    row: QueryRowSet
  ): Team {
    //
    val identifier = row[TableTeam.identifier]!!

    //
    val colorName = row[TableTeam.name]!!
    val color = TeamColors.findTeamColorByName(colorName)!!

    //
    val name = row[TableTeam.name]!!

    //
    return BaseTeam(
      name,
      color,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun findTeamByIdentifier(
    identifier: String
  ): Team? {
    return database.from(TableTeam)
      .select()
      .where {
        TableTeam.identifier eq identifier
      }
      .map {
        this.toTeam(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun findTeamByName(
    name: String
  ): Team? {
    return database.from(TableTeam)
      .select()
      .where {
        TableTeam.name eq name
      }
      .map {
        this.toTeam(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertTeam(
    teamIdentity: Team
  ) {
    database.insert(TableTeam) {
      set(it.identifier, teamIdentity.identifier)
      set(it.name, teamIdentity.name)
    }
  }
}