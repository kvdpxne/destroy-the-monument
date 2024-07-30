package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableGameArena
import me.kvdpxne.dtm.game.Arena
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoGameArena {

  /**
   * @since 0.1.0
   */
  fun findGameArenaByGameIdentifier(
    identifier: String
  ): List<Arena> {
    return database.from(TableGameArena)
      .select()
      .where {
        TableGameArena.gameIdentifier eq identifier
      }
      .mapNotNull {
        DaoArena.findArenaByIdentifierOrNull(
          it[TableGameArena.arenaIdentifier]!!
        )
      }
      .toList()
  }

  /**
   * @since 0.1.0
   */
  fun insertGameArena(
    gameIdentifier: String,
    arenaIdentifier: String
  ) {
    database.insert(TableGameArena) {
      set(it.gameIdentifier, gameIdentifier)
      set(it.arenaIdentifier, arenaIdentifier)
    }
  }
}