package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.arena.map.ArenaMap
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.shared.mapNotNullTo

/**
 * @since 0.1.0
 */
fun ArenaMap.toRawArenaMap(): RawArenaMap {
  return RawArenaMap(
    this.identifier,
    this.name
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<ArenaMap?>.toRawArenaMaps(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawArenaMap> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    ArenaMap::toRawArenaMap
  )
}