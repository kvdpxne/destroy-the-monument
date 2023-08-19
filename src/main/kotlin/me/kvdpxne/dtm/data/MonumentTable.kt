package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.Monument
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object MonumentTable : Table<Nothing>("monument") {

  val team = varchar("team")
  val x = int("x")
  val y = int("y")
  val z = int("z")

  val identifier = varchar("identifier").primaryKey()
}

object MonumentDao {

  fun findByIdentifier(identifier: UUID): Monument? {
    return database.from(MonumentTable)
      .select()
      .where {
        MonumentTable.identifier eq identifier.toString()
      }
      .map {
        val team = TeamDao.findByIdentifier(
          UUID.fromString(
            it[MonumentTable.team]
          )
        ) ?: return null

        Monument(
          team,
          it[MonumentTable.x]!!,
          it[MonumentTable.y]!!,
          it[MonumentTable.z]!!,
          UUID.fromString(it[MonumentTable.identifier])
        )
      }
      .firstOrNull()
  }

  fun insert(monument: Monument) {
    database.insert(MonumentTable) {
      set(it.identifier, monument.identifier.toString())
      set(it.team, monument.team.identifier.toString())
      set(it.x, monument.x)
      set(it.y, monument.y)
      set(it.z, monument.z)
    }
  }

  fun count(): Int {
    return database.from(ArenaTable)
      .select()
      .totalRecordsInAllPages
  }
}