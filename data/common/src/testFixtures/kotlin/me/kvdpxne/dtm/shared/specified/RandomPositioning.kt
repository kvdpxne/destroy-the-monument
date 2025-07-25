package me.kvdpxne.dtm.shared.specified

import me.kvdpxne.dtm.data.validation.rules.PositioningRules
import me.kvdpxne.dtm.shared.randomDouble
import me.kvdpxne.dtm.shared.randomFloat
import me.kvdpxne.dtm.shared.randomInt

/**
 * Type alias for positioning validation rules.
 *
 * @since 0.1.0
 */
private typealias rps = PositioningRules

/**
 * Generates a random pitch angle within Minecraft's valid range.
 *
 * ### Range
 * - Minimum: [PositioningRules.MIN_PITCH] (-90.0°)
 * - Maximum: [PositioningRules.MAX_PITCH] (90.0°)
 *
 * @return Random float within [-90.0, 90.0]
 * @since 0.1.0
 */
fun randomPitch(): Float = randomFloat(rps.MIN_PITCH, rps.MAX_PITCH)

/**
 * Generates a random yaw angle within Minecraft's valid range.
 *
 * ### Range
 * - Minimum: [PositioningRules.MIN_YAW] (-180.0°)
 * - Maximum: [PositioningRules.MAX_YAW] (180.0°)
 *
 * @return Random float within [-180.0, 180.0]
 * @since 0.1.0
 */
fun randomYaw(): Float = randomFloat(rps.MIN_YAW, rps.MAX_YAW)

/**
 * Generates a random integer X or Z coordinate within Minecraft's valid range.
 *
 * ### Range
 * - Minimum: [PositioningRules.MIN_XZ] (-29,999,984)
 * - Maximum: [PositioningRules.MAX_XZ] (29,999,984)
 *
 * @return Random integer within [-29,999,984, 29,999,984]
 * @since 0.1.0
 */
fun randomXzAxis(): Int = randomInt(rps.MIN_XZ, rps.MAX_XZ)

/**
 * Generates a random integer Y coordinate within Minecraft's overworld range.
 *
 * ### Range
 * - Minimum: [PositioningRules.MIN_OVERWORLD_Y] (-64)
 * - Maximum: [PositioningRules.MAX_OVERWORLD_Y] (320)
 *
 * @return Random integer within [-64, 320]
 * @since 0.1.0
 */
fun randomYAxis(): Int = randomInt(rps.MIN_OVERWORLD_Y, rps.MAX_OVERWORLD_Y)

/**
 * Generates a random precise X or Z coordinate as Double within Minecraft's valid range.
 *
 * ### Range
 * - Minimum: [PositioningRules.MIN_XZ] (-29,999,984.0)
 * - Maximum: [PositioningRules.MAX_XZ] (29,999,984.0)
 *
 * @return Random double within [-29,999,984.0, 29,999,984.0]
 * @since 0.1.0
 */
fun randomXzAxisPrecise(): Double = randomDouble(rps.MIN_XZ.toDouble(), rps.MAX_XZ.toDouble())

/**
 * Generates a random precise Y coordinate as Double within Minecraft's overworld range.
 *
 * ### Range
 * - Minimum: [PositioningRules.MIN_OVERWORLD_Y] (-64.0)
 * - Maximum: [PositioningRules.MAX_OVERWORLD_Y] (320.0)
 *
 * @return Random double within [-64.0, 320.0]
 * @since 0.1.0
 */
fun randomYAxisPrecise(): Double = randomDouble(rps.MIN_OVERWORLD_Y.toDouble(), rps.MAX_OVERWORLD_Y.toDouble())