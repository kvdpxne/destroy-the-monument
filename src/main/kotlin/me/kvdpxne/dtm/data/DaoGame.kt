package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableGame
import me.kvdpxne.dtm.game.BaseGame
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team
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
  private fun <T : Team> toGame(
    row: QueryRowSet
  ): Game<T> {
    //
    val identifier = row[TableGame.identifier]!!

    //
    val name = row[TableGame.name]!!

    return BaseGame(
      name,
      name,
      identifier =identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun <T : Team> findGames(): List<Game<T>> {
    return database.from(TableGame)
      .select()
      .mapNotNull {
        this.toGame<T>(it)
      }
      .toList()
  }

  /**
   * @since 0.1.0
   */
  fun <T : Team> findGameByIdentifierOrNull(
    identifier: String
  ): Game<T>? {
    return database.from(TableGame)
      .select()
      .where {
        TableGame.identifier eq identifier
      }
      .map {
        this.toGame<T>(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertGame(
    game: Game<*>
  ) {
    database.insert(TableGame) {
      set(it.identifier, game.identifier)
      set(it.name, game.name)
    }
  }
}