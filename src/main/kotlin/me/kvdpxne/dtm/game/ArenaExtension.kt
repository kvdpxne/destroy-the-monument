package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.ArenaMonumentsDao
import me.kvdpxne.dtm.data.ArenaSpawnPointsDao
import me.kvdpxne.dtm.data.MonumentDao
import me.kvdpxne.dtm.data.SpawnPointDao
import org.bukkit.Location

fun Arena.findMonument(location: Location) = location.run {
  findMonument(blockX, blockY, blockZ)
}

fun Arena.setSpawnPoint(team: TeamIdentity, position: Location) {
  val spawnPoint = RevivalPosition(position.x, position.y, position.z, position.pitch, position.yaw, team)
  _revivalPositions[team] = spawnPoint

  SpawnPointDao.insert(spawnPoint)
  ArenaSpawnPointsDao.insert(this, spawnPoint)
}

/**
 *
 */
fun Arena.addMonument(team: TeamIdentity, position: Location): Boolean {
  val monument = Monument(position.blockX, position.blockY, position.blockZ, team)

  MonumentDao.insert(monument)
  ArenaMonumentsDao.insert(this, monument)

  return addMonument(monument)
}