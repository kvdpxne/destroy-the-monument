package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.shared.mapNotNullTo
import me.kvdpxne.dtm.position.revival.RevivalPosition

/**
 * @since 0.1.0
 */
fun RevivalPosition.toRawRevivalPosition(): RawRevivalPosition {
  return RawRevivalPosition(
    this.identifier,
    this.team.toRawTeam(),
    this.x,
    this.z,
    this.y,
    this.pitch,
    this.yaw
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<RevivalPosition?>.toRawRevivalPositions(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawRevivalPosition> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    RevivalPosition::toRawRevivalPosition
  )
}