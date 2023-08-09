package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

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