package me.kvdpxne.dtm.game

import org.bukkit.Location

fun Arena.findMonument(location: Location) = location.run {
  findMonument(blockX, blockY, blockZ)
}