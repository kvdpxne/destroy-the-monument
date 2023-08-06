package me.kvdpxne.dtm.data

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object ArenaSpawnPointsTable : Table<Nothing>("arena_spawn_points") {

  val arena = varchar("arena")
  val spawnPoint = varchar("spawn_point")
}