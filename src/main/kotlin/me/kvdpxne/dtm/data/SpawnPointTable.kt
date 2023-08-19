package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.SpawnPoint
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.float
import org.ktorm.schema.varchar

object SpawnPointTable : Table<Nothing>("spawn_point") {

  val identifier = varchar("identifier").primaryKey()
  val team = varchar("team")
  val x = double("x")
  val y = double("y")
  val z = double("z")
  val pitch = float("pitch")
  val yaw = float("yaw")
}

object SpawnPointDao {

  fun findByIdentifier(identifier: UUID): SpawnPoint? {
    return database.from(SpawnPointTable)
      .select()
      .where {
        SpawnPointTable.identifier eq identifier.toString()
      }
      .map {
        val team = TeamDao.findByIdentifier(
          UUID.fromString(
            it[SpawnPointTable.team]
          )
        ) ?: return null

        SpawnPoint(
          team,
          it[SpawnPointTable.x]!!,
          it[SpawnPointTable.y]!!,
          it[SpawnPointTable.z]!!,
          it[SpawnPointTable.pitch]!!,
          it[SpawnPointTable.yaw]!!,
          UUID.fromString(it[SpawnPointTable.identifier])
        )
      }
      .firstOrNull()
  }

  fun insert(spawnPoint: SpawnPoint) {
    database.insert(SpawnPointTable) {
      set(SpawnPointTable.identifier, spawnPoint.identifier.toString())
      set(SpawnPointTable.team, spawnPoint.team.identifier.toString())
      set(SpawnPointTable.x, spawnPoint.x)
      set(SpawnPointTable.y, spawnPoint.y)
      set(SpawnPointTable.z, spawnPoint.z)
      set(SpawnPointTable.pitch, spawnPoint.pitch)
      set(SpawnPointTable.yaw, spawnPoint.yaw)
    }
  }
}