package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Arena
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

object ArenaTable : Table<Nothing>("arena") {

  var identifier = varchar("identifier").primaryKey()
  val name = varchar("name")
}

object ArenaDao {

  fun findArenaByIdentifier(identifier: UUID): Arena? {
    return database.from(ArenaTable)
      .select()
      .where { ArenaTable.identifier eq identifier.toString() }
      .map {
        val uuid = UUID.fromString(it[ArenaTable.identifier])

        val name = it[ArenaTable.name]!!

        Arena(uuid, name)
      }
      .firstOrNull()
  }

  fun count(): Int {
    return database.from(ArenaTable)
      .select()
      .totalRecordsInAllPages
  }
}