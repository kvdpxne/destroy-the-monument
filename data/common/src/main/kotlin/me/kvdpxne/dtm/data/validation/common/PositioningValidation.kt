package me.kvdpxne.dtm.data.validation.common

import kotlin.math.roundToInt
import me.kvdpxne.dtm.ChangesByVersion
import me.kvdpxne.dtm.data.validation.rules.PositioningRules
import me.kvdpxne.notchity.MinecraftVersionCreator

/**
 * Validates if a coordinate value fits within Minecraft's world boundaries.
 *
 * Handles both integer and decimal values by rounding decimals before validation.
 * Only supports Int and Double types - other number types will cause errors.
 *
 * @param v Coordinate value (X, Y, or Z axis)
 * @param range Valid integer range for this coordinate
 * @return True if value fits within range after rounding, false otherwise
 * @throws IllegalArgumentException If non-supported number type is used
 * @since 0.1.0
 */
private fun <T : Number> isPositionAxisValid(
  v: T,
  range: IntRange,
): Boolean {
  return when (v) {
    is Int -> v in range
    is Double -> v.roundToInt() in range
    else -> error("An unsupported data type was passed to validate the X or Z axes.")
  }
}

/**
 * Checks if X or Z coordinates are within Minecraft's horizontal world limits.
 *
 * Uses standard X/Z boundaries defined in [PositioningRules] (from [PositioningRules.MIN_XZ] to
 * [PositioningRules.MAX_XZ]). Accepts both whole numbers and decimal values (which get rounded).
 *
 * @param xz Coordinate value (X or Z axis)
 * @return True if coordinate fits within horizontal limits, false otherwise
 * @since 0.1.0
 */
fun <T : Number> isPositionXzValid(
  xz: T
): Boolean {
  return isPositionAxisValid(xz, PositioningRules.MIN_XZ..PositioningRules.MAX_XZ)
}

/**
 * Validates Y coordinates based on Minecraft version-specific height limits.
 *
 * Modern versions use [PositioningRules.MIN_OVERWORLD_Y] to [PositioningRules.MAX_OVERWORLD_Y],
 * while older versions use legacy bounds ([PositioningRules.MIN_OVERWORLD_LEGACY_Y] to
 * [PositioningRules.MAX_OVERWORLD_LEGACY_Y]). Automatically adapts to current game version
 * through [MinecraftVersionCreator].
 *
 * @param y Vertical coordinate value
 * @return True if Y value fits version-specific limits, false otherwise
 * @throws IllegalStateException If game version detection fails
 * @since 0.1.0
 */
fun <T : Number> isPositionYValid(
  y: T
): Boolean {
  val version: Int = MinecraftVersionCreator.getMinecraftVersion().number
  return if (ChangesByVersion.HEIGHT_LIMIT <= version) {
    isPositionAxisValid(
      y,
      PositioningRules.MIN_OVERWORLD_Y..PositioningRules.MAX_OVERWORLD_Y
    )
  } else {
    isPositionAxisValid(
      y,
      PositioningRules.MIN_OVERWORLD_LEGACY_Y..PositioningRules.MAX_OVERWORLD_LEGACY_Y
    )
  }
}