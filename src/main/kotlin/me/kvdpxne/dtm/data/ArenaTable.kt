package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaMap
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object ArenaTable : Table<Nothing>("arena") {

  val name = varchar("name")
  val worldIdentifier = varchar("world_identifier")
  val worldName = varchar("world_name")

  val identifier = varchar("identifier").primaryKey()
}

object ArenaDao {

  fun findAll(): Collection<Arena> {
    return database.from(ArenaTable)
      .innerJoin(ArenaMonumentsTable, ArenaMonumentsTable.arena eq ArenaTable.identifier)
      .innerJoin(ArenaSpawnPointsTable, ArenaSpawnPointsTable.arena eq ArenaTable.identifier)
      .select()
      .map {
        val uuid = UUID.fromString(it[ArenaTable.identifier])
        val monuments = ArenaMonumentsDao.findAllByArenaIdentifier(uuid)
        val spawnPoints = ArenaSpawnPointsDao.findAllByArenaIdentifier(uuid)

        Arena(
          uuid,
          it[ArenaTable.name]!!
        ).apply {
          map = ArenaMap(
            UUID.fromString(it[ArenaTable.worldIdentifier]),
            it[ArenaTable.worldName]!!
          )

          spawnPoints.forEach { spawnPoint ->
            setSpawnPoint(spawnPoint)
          }

          monuments.forEach { monument ->
            addMonument(monument)
          }
        }
      }
  }

  fun findByIdentifier(identifier: UUID): Arena? {
    return database.from(ArenaTable)
      .innerJoin(ArenaMonumentsTable, ArenaMonumentsTable.arena eq ArenaTable.identifier)
      .innerJoin(ArenaSpawnPointsTable, ArenaSpawnPointsTable.arena eq ArenaTable.identifier)
      .select()
      .where {
        ArenaTable.identifier eq identifier.toString()
      }
      .map {
        val uuid = UUID.fromString(it[ArenaTable.identifier])
        val monuments = ArenaMonumentsDao.findAllByArenaIdentifier(uuid)
        val spawnPoints = ArenaSpawnPointsDao.findAllByArenaIdentifier(uuid)

        Arena(
          uuid,
          it[ArenaTable.name]!!
        ).apply {
          map = ArenaMap(
            UUID.fromString(it[ArenaTable.worldIdentifier]),
            it[ArenaTable.worldName]!!
          )

          spawnPoints.forEach { spawnPoint ->
            setSpawnPoint(spawnPoint)
          }

          monuments.forEach { monument ->
            addMonument(monument)
          }
        }
      }
      .firstOrNull()
  }

  private fun insertMonuments(arena: Arena) {
    arena.monuments.values.forEach {
      ArenaMonumentsDao.insertAll(arena, it)
    }
  }

  private fun insertSpawnPoints(arena: Arena) {
    arena.spawnPoints.values.forEach {
      ArenaSpawnPointsDao.insert(arena, it)
    }
  }

  fun insert(arena: Arena) {
    database.insert(ArenaTable) {
      set(it.identifier, arena.identifier.toString())
      set(it.name, arena.name)
      set(it.worldIdentifier, arena.map?.identifier.toString())
      set(it.worldName, arena.map?.name)

      insertMonuments(arena)
      insertSpawnPoints(arena)
    }
  }

  fun update(arena: Arena) {
    database.update(ArenaTable) {
      set(it.name, arena.name)
      set(it.worldIdentifier, arena.map?.identifier.toString())
      set(it.worldName, arena.map?.name)

      where {
        it.identifier eq arena.identifier.toString()
      }
    }
  }

  fun count(): Int {
    return database.from(ArenaTable)
      .select()
      .totalRecordsInAllPages
  }
}