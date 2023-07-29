package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Arena
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

object ArenaTable : Table<Nothing>("area") {

  var identifier = varchar("identifier").primaryKey()
  val name = varchar("name")

  var world = varchar("world")
}

object ArenaDao {

  fun findArenaByIdentifier(identifier: UUID): Arena? {
    return database.from(ArenaTable)
      .select()
      .where { ArenaTable.identifier eq identifier.toString() }
      .map {
        val uuid = UUID.fromString(it[ArenaTable.identifier])

        val name = it[ArenaTable.name]!!
        val world = it[ArenaTable.world]

        Arena(uuid, name, world)
      }
      .firstOrNull()
  }

  fun count(): Int {
    return database.from(ArenaTable)
      .select()
      .totalRecordsInAllPages
  }
}