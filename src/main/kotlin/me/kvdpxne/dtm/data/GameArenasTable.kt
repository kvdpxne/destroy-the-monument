package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object GameArenasTable : Table<Nothing>("game_arenas") {

  var game = varchar("game")
  val arena = varchar("arena")
}

object GameArenasDao {

  fun findAllByGameIdentifier(identifier: UUID): Collection<Arena> {
    return database.from(GameArenasTable)
      .select()
      .where {
        GameArenasTable.game eq identifier.toString()
      }
      .mapNotNull {
        ArenaDao.findByIdentifier(
          UUID.fromString(
            it[GameArenasTable.arena]
          )
        )
      }
  }

  fun insert(game: Game, arena: Arena) {
    database.insert(GameArenasTable) {
      set(it.game, game.identifier.toString())
      set(it.arena, arena.identifier.toString())
    }
  }
}