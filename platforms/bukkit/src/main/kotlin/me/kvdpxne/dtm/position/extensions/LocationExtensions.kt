package me.kvdpxne.dtm.position.extensions

import me.kvdpxne.dtm.position.BasicBlockPosition
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.BukkitBlockPosition
import org.bukkit.Location

fun Location.multidimensionalBlockPosition(): BlockPosition {
  return BasicBlockPosition(
    this.blockX,
    this.blockY,
    this.blockZ
  )
}

fun Location.dimensionalBlockPosition(): BlockPosition {
  return BukkitBlockPosition(
    this.blockX,
    this.blockY,
    this.blockZ,
    this.world.uid,
    this.world.name
  )
}