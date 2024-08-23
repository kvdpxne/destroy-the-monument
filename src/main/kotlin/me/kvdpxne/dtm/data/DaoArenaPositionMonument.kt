package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableArenaPositionMonument
import me.kvdpxne.dtm.game.MonumentPosition
import me.kvdpxne.dtm.game.Team
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoArenaPositionMonument {

  /**
   * @since 0.1.0
   */
  fun findArenaPositionMonumentByArenaIdentifier(
    identifier: String
  ): List<MonumentPosition<*>> {
    return database.from(TableArenaPositionMonument)
      .select()
      .where {
        TableArenaPositionMonument.arenaIdentifier eq identifier
      }
      .mapNotNull {
        DaoPositionMonument.findPositionMonumentByIdentifierOrNull<Team>(
          it[TableArenaPositionMonument.positionMonumentIdentifier]!!
        )
      }
      .toList()
  }

  /**
   * @since 0.1.0
   */
  fun insertArenaPositionMonument(
    arenaIdentifier: String,
    positionMonumentIdentifier: String
  ) {
    database.insert(TableArenaPositionMonument) {
      set(it.arenaIdentifier, arenaIdentifier)
      set(it.positionMonumentIdentifier, positionMonumentIdentifier)
    }
  }

  /**
   * @since 0.1.0
   */
  fun deleteArenaPositionMonument(
    positionMonumentIdentifier: String
  ) {
    database.delete(TableArenaPositionMonument) {
      it.positionMonumentIdentifier eq positionMonumentIdentifier
    }
  }
}