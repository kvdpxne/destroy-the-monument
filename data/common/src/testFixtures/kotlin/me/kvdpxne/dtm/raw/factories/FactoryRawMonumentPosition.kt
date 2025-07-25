package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.shared.randomInt
import me.kvdpxne.dtm.shared.specified.randomXzAxis
import me.kvdpxne.dtm.shared.specified.randomYAxis
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * Factory function that generates a [RawMonumentPosition] (team monument location) with
 * randomized or configurable properties. Coordinates are randomized by default, with
 * Y-axis constrained to valid world heights (0-255).
 *
 * @param previousIdentifier Optional UUID to avoid when generating a new identifier.
 * @param identifier Unique monument ID (default: new UUID unique relative to `previousIdentifier`).
 * @param team Associated [RawTeam] (default: new randomized team via `makeRawTeam()`).
 * @param x X-coordinate in world (default: random integer).
 * @param z Z-coordinate in world (default: random integer).
 * @param y Y-coordinate (height) constrained to 0-254 (default: random integer in range).
 * @return Configured [RawMonumentPosition] instance.
 *
 * @since 0.1.0
 * @see uniqueUuid
 * @see randomInt
 * @see makeRawTeam
 */
fun makeRawMonumentPosition(
  // @formatter:off
  previousIdentifier: UUID?   = null,
  identifier        : UUID    = uniqueUuid(previousIdentifier),
  team              : RawTeam = makeRawTeam(),
  x                 : Int     = randomXzAxis(),
  z                 : Int     = randomXzAxis(),
  y                 : Int     = randomYAxis()
  // @formatter:on
) = RawMonumentPosition(
  identifier,
  team,
  x,
  y,
  z
)