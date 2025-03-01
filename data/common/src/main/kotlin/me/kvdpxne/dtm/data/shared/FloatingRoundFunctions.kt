package me.kvdpxne.dtm.data.shared

import kotlin.math.round

/**
 * @since 0.1.0
 */
infix fun Float.roundTo(
  digits: Int
): Float {
  var multiplier = 1.0F
  var i = 0

  do {
    multiplier *= 10
    ++i
  } while (i < digits)

  return round(this * multiplier) / multiplier
}