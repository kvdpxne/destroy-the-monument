package me.kvdpxne.dtm.shared.world

import me.kvdpxne.dtm.position.BaseBlockPosition
import me.kvdpxne.dtm.position.BaseEntityPosition
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.EntityPosition
import me.kvdpxne.dtm.position.toWorld
import org.bukkit.Location
import org.bukkit.World

fun Location.toBlockPosition(): BlockPosition {
  return BaseBlockPosition(
    this.blockX,
    this.blockY,
    this.blockZ,
    this.world.name
  )
}

fun Location.toEntityPosition(): EntityPosition {
  return BaseEntityPosition(
    this.x,
    this.y,
    this.z,
    this.pitch,
    this.yaw,
    this.world.name
  )
}

fun EntityPosition.toLocation(world: World? = null): Location {
  return Location(world ?: this.toWorld(), this.x, this.y, this.z, this.yaw, this.pitch)
}