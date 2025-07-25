package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.shared.mapNotNullTo
import me.kvdpxne.dtm.position.monument.MonumentPosition

/**
 * @since 0.1.0
 */
fun MonumentPosition.toRawMonumentPosition(): RawMonumentPosition {
  return RawMonumentPosition(
    this.identifier,
    this.team.toRawTeam(),
    this.x,
    this.z,
    this.y
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<MonumentPosition?>.toRawMonumentPositions(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawMonumentPosition> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    MonumentPosition::toRawMonumentPosition
  )
}
