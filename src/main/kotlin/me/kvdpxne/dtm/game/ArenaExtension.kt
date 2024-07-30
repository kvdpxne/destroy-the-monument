package me.kvdpxne.dtm.game

import org.bukkit.Location

/**
 * @since 0.1.0
 */
fun Arena.findMonument(
  location: Location,
  sameWorld: Boolean = false
): MonumentPosition? {
  if (sameWorld && location.world.uid != this.map?.identifier) {
    return null
  }

  return this.findMonument(
    location.blockX,
    location.blockY,
    location.blockZ,
  )
}