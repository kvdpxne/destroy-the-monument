package me.kvdpxne.dtm.shared.basics

import me.kvdpxne.dtm.shared.basics.position.EntityPosition
import me.kvdpxne.dtm.shared.basics.position.Position
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

fun <T : Number> Position<T>.toWorld(): World? {
  return this.worldName?.let {
    Bukkit.getWorld(it)
  }
}

fun EntityPosition.isNear(location: Location, radius: Double): Boolean {
  return this.isNear(location.x, location.y, location.z, radius)
}