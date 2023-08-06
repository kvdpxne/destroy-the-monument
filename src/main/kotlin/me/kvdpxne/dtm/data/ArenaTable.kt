package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaMap
import org.bukkit.Bukkit
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

object ArenaTable : Table<Nothing>("arena") {

  val name = varchar("name")
  val worldIdentifier = varchar("world_identifier")
  val worldName = varchar("world_name")

  val identifier = varchar("identifier").primaryKey()
}

object ArenaDao {

  fun findArenaByIdentifier(identifier: UUID): Arena? {
    return database.from(ArenaTable)
      .select()
      .where { ArenaTable.identifier eq identifier.toString() }
      .map {

        val uuid = UUID.fromString(it[ArenaTable.identifier])
        val name = it[ArenaTable.name]!!
        val worldUuid = UUID.fromString(it[ArenaTable.worldIdentifier])
        val worldName = it[ArenaTable.worldName]!!

        Arena(uuid, name).apply {
          map = ArenaMap(worldUuid, worldName)
        }
      }
      .firstOrNull()
  }

  fun insert(arena: Arena) {
    database.insert(ArenaTable) {
      set(it.identifier, arena.identifier.toString())
      set(it.name, arena.name)
      set(it.worldIdentifier, arena.map?.identifier.toString())
      set(it.worldName, arena.map?.name)
    }
  }

  fun count(): Int {
    return database.from(ArenaTable)
      .select()
      .totalRecordsInAllPages
  }
}