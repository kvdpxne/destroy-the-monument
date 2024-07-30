package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableTeam
import me.kvdpxne.dtm.game.TeamIdentity
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
  ): TeamIdentity {
    //
    val identifier = row[TableTeam.identifier]!!

    //
    val name = row[TableTeam.name]!!
    val colorInChat = row[TableTeam.colorInChat]!!
    val professionColor = row[TableTeam.professionColor]!!
    val dyeColor = row[TableTeam.dyeColor]!!

    //
    return TeamIdentity(
      name,
      colorInChat,
      professionColor,
      dyeColor,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun findTeamByIdentifier(
    identifier: String
  ): TeamIdentity? {
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
  ): TeamIdentity? {
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
    teamIdentity: TeamIdentity
  ) {
    database.insert(TableTeam) {
      set(it.identifier, teamIdentity.identifier)
      set(it.name, teamIdentity.name)
      set(it.colorInChat, teamIdentity.colorInChat)
      set(it.professionColor, teamIdentity.professionColor)
      set(it.dyeColor, teamIdentity.dyeColor)
    }
  }
}