package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableArenaPositionRevival
import me.kvdpxne.dtm.game.RevivalPosition
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
object DaoArenaPositionRevival {

  /**
   * @since 0.1.0
   */
  fun findArenaPositionRevivalByArenaIdentifier(
    identifier: String
  ): List<RevivalPosition<*>> {
    return database.from(TableArenaPositionRevival)
      .select()
      .where {
        TableArenaPositionRevival.arenaIdentifier eq identifier
      }
      .mapNotNull {
        DaoPositionRevival.findPositionRevivalByIdentifierOrNull<Team>(
          it[TableArenaPositionRevival.positionRevivalIdentifier]!!
        )
      }
      .toList()
  }

  /**
   * @since 0.1.0
   */
  fun insertArenaPositionRevival(
    arenaIdentifier: String,
    positionRevivalIdentifier: String
  ) {
    database.insert(TableArenaPositionRevival) {
      set(it.arenaIdentifier, arenaIdentifier)
      set(it.positionRevivalIdentifier, positionRevivalIdentifier)
    }
  }

  /**
   * @since 0.1.0
   */
  fun deleteArenaPositionRevival(
    positionRevivalIdentifier: String
  ) {
    database.delete(TableArenaPositionRevival) {
      it.positionRevivalIdentifier eq positionRevivalIdentifier
    }
  }
}