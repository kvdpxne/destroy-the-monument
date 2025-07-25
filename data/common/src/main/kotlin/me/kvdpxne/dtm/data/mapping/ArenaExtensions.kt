package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.data.raw.RawArena
import me.kvdpxne.dtm.data.shared.mapNotNullTo

/**
 * @since 0.1.0
 */
fun Arena.toRawArena(): RawArena {
  return RawArena(
    this.identifier,
    this.map.toRawArenaMap(),
    this.monumentPositions.toRawMonumentPositions(),
    this.revivalPositions.toRawRevivalPositions(),
    this.name
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<Arena?>.toRawArenas(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawArena> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    Arena::toRawArena
  )
}