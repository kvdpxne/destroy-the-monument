package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.SpawnPoint
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object ArenaSpawnPointsTable : Table<Nothing>("arena_spawn_points") {

  val arena = varchar("arena")
  val spawnPoint = varchar("spawn_point")
}

object ArenaSpawnPointsDao {

  fun findAllByArenaIdentifier(identifier: UUID): Collection<SpawnPoint> {
    return database.from(ArenaSpawnPointsTable)
      .select()
      .where {
        ArenaSpawnPointsTable.arena eq identifier.toString()
      }
      .mapNotNull {
        SpawnPointDao.findByIdentifier(
          UUID.fromString(
            it[ArenaSpawnPointsTable.spawnPoint]
          )
        )
      }
  }

  fun insert(arena: Arena, spawnPoint: SpawnPoint) {
    database.insert(ArenaSpawnPointsTable) {
      set(ArenaSpawnPointsTable.arena, arena.identifier.toString())
      set(ArenaSpawnPointsTable.spawnPoint, spawnPoint.identifier.toString())
    }
  }
}