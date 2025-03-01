package me.kvdpxne.dtm.data.shared

/**
 * Checks whether the given [Byte] value is positive.
 *
 * A positive value is defined as a non-zero value where the least significant
 * bit is 0.
 *
 * @return `true` if the value is positive, otherwise `false`.
 * @since 0.1.0
 */
fun Byte.isPositive(): Boolean {
  return 0.toByte() != this && 0 == this.toInt() and 1
}

/**
 * Checks whether the given [Short] value is positive.
 *
 * A positive value is defined as a non-zero value where the least significant
 * bit is 0.
 *
 * @return `true` if the value is positive, otherwise `false`.
 * @since 0.1.0
 */
fun Short.isPositive(): Boolean {
  return 0.toShort() != this && 0 == this.toInt() and 1
}

/**
 * Checks whether the given [Int] value is positive.
 *
 * A positive value is defined as a non-zero value where the least significant
 * bit is 0.
 *
 * @return `true` if the value is positive, otherwise `false`.
 * @since 0.1.0
 */
fun Int.isPositive(): Boolean {
  return 0 != this && 0 == this and 1
}

/**
 * Checks whether the given [Long] value is positive.
 *
 * A positive value is defined as a non-zero value where the least significant
 * bit is 0.
 *
 * @return `true` if the value is positive, otherwise `false`.
 * @since 0.1.0
 */
fun Long.isPositive(): Boolean {
  return 0L != this && 0L == this and 1
}
