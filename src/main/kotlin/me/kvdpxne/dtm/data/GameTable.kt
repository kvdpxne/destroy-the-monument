package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Game
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

object GameTable : Table<Nothing>("game") {

  val name = varchar("name")

  var identifier = varchar("identifier").primaryKey()
}

object GameDao {

  fun findGameByIdentifier(identifier: UUID): Game? {
    return database.from(GameTable)
      .innerJoin(GameArenasTable, on = GameTable.identifier eq GameArenasTable.game)
      .select()
      .where { GameTable.identifier eq identifier.toString() }
      .map {
        val uuid = UUID.fromString(it[GameTable.identifier])

        val name = it[GameTable.name]!!

        Game(uuid, name)
      }
      .firstOrNull()
  }

  fun count(): Int {
    return database.from(ArenaTable)
      .select()
      .totalRecordsInAllPages
  }
}