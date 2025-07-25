package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.shared.randomDouble
import me.kvdpxne.dtm.shared.randomFloat
import me.kvdpxne.dtm.shared.specified.randomPitch
import me.kvdpxne.dtm.shared.specified.randomXzAxisPrecise
import me.kvdpxne.dtm.shared.specified.randomYAxisPrecise
import me.kvdpxne.dtm.shared.specified.randomYaw
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * Factory function that generates a [RawRevivalPosition] (team respawn point) with
 * randomized or configurable properties. Coordinates and angles are randomized with
 * safe defaults, including Y-axis constraint for valid world heights.
 *
 * @param previousIdentifier Optional UUID to avoid when generating a new identifier.
 * @param identifier Unique revival point ID (default: new UUID unique relative to `previousIdentifier`).
 * @param team Associated [RawTeam] (default: new randomized team via `makeRawTeam()`).
 * @param x X-coordinate in world (default: random double).
 * @param z Z-coordinate in world (default: random double).
 * @param y Y-coordinate (height) constrained to 0.0-255.0 (default: random double in range).
 * @param pitch Vertical view angle (-90° looking up ↔ 90° looking down) (default: random float in 0°-180°).
 * @param yaw Horizontal view angle (default: random float in -90° to 90°).
 * @return Configured [RawRevivalPosition] instance.
 *
 * @since 0.1.0
 * @see uniqueUuid
 * @see randomDouble
 * @see randomFloat
 * @see makeRawTeam
 */
fun makeRawRevivalPosition(
  // @formatter:off
  previousIdentifier: UUID?   = null,
  identifier        : UUID    = uniqueUuid(previousIdentifier),
  team              : RawTeam = makeRawTeam(),
  x                 : Double  = randomXzAxisPrecise(),
  z                 : Double  = randomXzAxisPrecise(),
  y                 : Double  = randomYAxisPrecise(),
  pitch             : Float   = randomPitch(),
  yaw               : Float   = randomYaw(),
  // @formatter:on
) = RawRevivalPosition(
  identifier,
  team,
  x,
  y,
  z,
  pitch,
  yaw
)