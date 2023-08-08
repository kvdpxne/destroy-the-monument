package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Monument
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

object ArenaMonumentsTable : Table<Nothing>("arena_monuments") {

  val arena = varchar("arena")
  val monument = varchar("monument")
}

object ArenaMonumentsDao {

  fun findAllByArenaIdentifier(identifier: UUID): Collection<Monument> {
    return database.from(ArenaMonumentsTable)
      .select()
      .where {
        ArenaMonumentsTable.arena eq identifier.toString()
      }
      .mapNotNull {
        MonumentDao.findByIdentifier(
          UUID.fromString(
            it[ArenaMonumentsTable.monument]
          )
        )
      }
  }

  fun insert(arena: Arena, monument: Monument) {
    database.insert(ArenaMonumentsTable) {
      set(ArenaMonumentsTable.arena, arena.identifier.toString())
      set(ArenaMonumentsTable.monument, monument.identifier.toString())
    }
  }

  fun insertAll(arena: Arena, monuments: Collection<Monument>) {
    monuments.forEach {
      insert(arena, it)
    }
  }
}