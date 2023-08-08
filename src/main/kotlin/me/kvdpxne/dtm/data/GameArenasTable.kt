package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game
import org.ktorm.dsl.insert
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

object GameArenasTable : Table<Nothing>("game_arenas") {

  var game = varchar("game")
  val arena = varchar("arena")
}

object GameArenasDao {

  fun insert(game: Game, arena: Arena) {
    database.insert(GameArenasTable) {
      set(it.game, game.identifier.toString())
      set(it.arena, arena.identifier.toString())
    }
  }
}