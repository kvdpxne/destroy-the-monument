package me.kvdpxne.dtm.shared.minecraft.bukkit

import me.kvdpxne.dtm.shared.basics.position.BaseBlockPosition
import me.kvdpxne.dtm.shared.basics.position.BaseEntityPosition
import me.kvdpxne.dtm.shared.basics.position.BlockPosition
import me.kvdpxne.dtm.shared.basics.position.EntityPosition
import me.kvdpxne.dtm.shared.basics.toWorld
import org.bukkit.Location
import org.bukkit.World

fun Location.toBlockPosition(): BlockPosition {
  return BaseBlockPosition(this.blockX, this.blockY, this.blockZ, this.world.name)
}

fun Location.toEntityPosition(): EntityPosition {
  return BaseEntityPosition(this.x, this.y, this.z, this.pitch, this.yaw, this.world.name)
}

fun EntityPosition.toLocation(world: World? = null): Location {
  return Location(world ?: this.toWorld(), this.x, this.y, this.z, this.pitch, this.yaw)
}