package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableGame
import me.kvdpxne.dtm.game.temporary.Game
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoGame {

  /**
   * @since 0.1.0
   */
  private fun toGame(
    row: QueryRowSet
  ): Game {
    //
    val identifier = row[TableGame.identifier]!!

    //
    val name = row[TableGame.name]!!

    return Game(
      name,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun findGames(): List<Game> {
    return database.from(TableGame)
      .select()
      .mapNotNull {
        toGame(it)
      }
      .toList()
  }

  /**
   * @since 0.1.0
   */
  fun findGameByIdentifierOrNull(
    identifier: String
  ): Game? {
    return database.from(TableGame)
      .select()
      .where {
        TableGame.identifier eq identifier
      }
      .map {
        toGame(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertGame(
    game: Game
  ) {
    database.insert(TableGame) {
      set(it.identifier, game.identifier)
      set(it.name, game.name)
    }
  }
}