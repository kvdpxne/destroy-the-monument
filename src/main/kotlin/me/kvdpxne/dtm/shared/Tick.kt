package me.kvdpxne.dtm.shared

/**
 * Represents a unit of time in ticks.
 *
 * A single tick is equivalent to 50 milliseconds in Minecraft's
 * internal time system.
 *
 * @since 0.1.0
 */
typealias Tick = Int

/**
 * Converts the tick value to seconds.
 *
 * @return the duration in seconds.
 * @since 0.1.0
 */
val Tick.seconds: Int
  get() = this * 20

/**
 * Converts the tick value to minutes.
 *
 * @return the duration in minutes.
 * @since 0.1.0
 */
val Tick.minutes: Int
  get() = this * 20 * 60

/**
 * Converts the tick value to hours.
 *
 * @return the duration in hours.
 * @since 0.1.0
 */
val Tick.hours: Int
  get() = this * 20 * 60 * 60