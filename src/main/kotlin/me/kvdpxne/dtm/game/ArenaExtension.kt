package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.ArenaMonumentsDao
import me.kvdpxne.dtm.data.ArenaSpawnPointsDao
import me.kvdpxne.dtm.data.MonumentDao
import me.kvdpxne.dtm.data.SpawnPointDao
import me.kvdpxne.dtm.shared.Identity
import org.bukkit.Location

fun Arena.findMonument(location: Location) = location.run {
  findMonument(blockX, blockY, blockZ)
}

fun Arena.setSpawnPoint(team: Identity, position: Location) {
  val spawnPoint = SpawnPoint(team, position.x, position.y, position.z, position.pitch, position.yaw)
  spawnPoints[team] = spawnPoint

  SpawnPointDao.insert(spawnPoint)
  ArenaSpawnPointsDao.insert(this, spawnPoint)
}

/**
 *
 */
fun Arena.addMonument(team: Identity, position: Location): Boolean {
  val monument = Monument(team, position.blockX, position.blockY, position.blockZ)

  MonumentDao.insert(monument)
  ArenaMonumentsDao.insert(this, monument)

  return addMonument(monument)
}