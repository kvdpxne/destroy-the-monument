package me.kvdpxne.dtm.shared.basics

import org.bukkit.Location

fun EntityPosition.isNear(location: Location, radius: Double): Boolean {
  return this.isNear(location.x, location.y, location.z, radius)
}