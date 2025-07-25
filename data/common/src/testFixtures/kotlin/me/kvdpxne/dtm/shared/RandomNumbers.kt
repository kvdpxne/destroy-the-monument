package me.kvdpxne.dtm.shared

import kotlin.random.Random

/**
 * Generates a random Boolean value (`true` or `false`) with equal probability.
 *
 * Example:
 * ```
 * val flag = randomBoolean()  // 50% chance of true/false
 * ```
 *
 * @return Randomly generated `Boolean`.
 * @since 0.1.0
 */
fun randomBoolean() = Random.nextBoolean()

/**
 * Generates a random `Int` within the specified range [`from`, `until`).
 * - Default: Full `Int` range (`Int.MIN_VALUE` to `Int.MAX_VALUE - 1`).
 *
 * Example:
 * ```
 * val num = randomInt(from = 10, until = 20)  // ∈ [10, 19]
 * ```
 *
 * @param from Inclusive lower bound (default: `Int.MIN_VALUE`).
 * @param until Exclusive upper bound (default: `Int.MAX_VALUE`).
 * @return Random `Int` in the specified range.
 * @throws IllegalArgumentException if `from >= until`.
 * @since 0.1.0
 */
fun randomInt(
  from: Int = Int.MIN_VALUE,
  until: Int = Int.MAX_VALUE
) = Random.nextInt(from, until)

/**
 * Generates a random **non-negative** `Int` within [`from`, `until`).
 * - Default: `0` to `Int.MAX_VALUE - 1`.
 *
 * Example:
 * ```
 * val num = randomPositiveInt(until = 100)  // ∈ [0, 99]
 * ```
 *
 * @param from Inclusive lower bound (≥0, default: `0`).
 * @param until Exclusive upper bound (default: `Int.MAX_VALUE`).
 * @return Random non-negative `Int`.
 * @throws IllegalArgumentException if `from < 0` or `from >= until`.
 * @since 0.1.0
 */
fun randomPositiveInt(
  from: Int = 0,
  until: Int = Int.MAX_VALUE
) = Random.nextInt(from, until)

/**
 * Generates a random `Long` within the specified range [`from`, `until`).
 * - Default: Full `Long` range.
 *
 * Example:
 * ```
 * val bigNum = randomLong(until = 1_000_000L)  // ∈ [Long.MIN_VALUE, 999_999]
 * ```
 *
 * @param from Inclusive lower bound (default: `Long.MIN_VALUE`).
 * @param until Exclusive upper bound (default: `Long.MAX_VALUE`).
 * @return Random `Long` in the specified range.
 * @throws IllegalArgumentException if `from >= until`.
 * @since 0.1.0
 */
fun randomLong(
  from: Long = Long.MIN_VALUE,
  until: Long = Long.MAX_VALUE
) = Random.nextLong(from, until)

/**
 * Generates a random **non-negative** `Long` within [`from`, `until`).
 * - Default: `0` to `Long.MAX_VALUE - 1`.
 *
 * Example:
 * ```
 * val bigNum = randomPositiveLong(from = 1L, until = 100L)  // ∈ [1, 99]
 * ```
 *
 * @param from Inclusive lower bound (≥0, default: `0`).
 * @param until Exclusive upper bound (default: `Long.MAX_VALUE`).
 * @return Random non-negative `Long`.
 * @throws IllegalArgumentException if `from < 0` or `from >= until`.
 * @since 0.1.0
 */
fun randomPositiveLong(
  from: Long = 0,
  until: Long = Long.MAX_VALUE
) = Random.nextLong(from, until)

/**
 * Generates a random `Float` within the range [`from`, `until`).
 * - Default: `0.0F` to `Float.MAX_VALUE`.
 *
 * Example:
 * ```
 * val float = randomFloat(from = 0.5F, until = 5.0F)  // ∈ [0.5, 5.0)
 * ```
 *
 * @param from Inclusive lower bound (default: `0.0F`).
 * @param until Exclusive upper bound (default: `Float.MAX_VALUE`).
 * @return Random `Float` in the specified range.
 * @since 0.1.0
 */
fun randomFloat(
  from: Float = 0F,
  until: Float = Float.MAX_VALUE
) = Random.nextFloat() * (until - from) + from

/**
 * Generates a random `Double` within the range [`from`, `until`).
 * - Default: `0.0` to `Double.MAX_VALUE`.
 *
 * Example:
 * ```
 * val double = randomDouble(until = 1.0)  // ∈ [0.0, 1.0)
 * ```
 *
 * @param from Inclusive lower bound (default: `0.0`).
 * @param until Exclusive upper bound (default: `Double.MAX_VALUE`).
 * @return Random `Double` in the specified range.
 * @since 0.1.0
 */
fun randomDouble(
  from: Double = 0.0,
  until: Double = Double.MAX_VALUE
) = Random.nextDouble() * (until - from) + from