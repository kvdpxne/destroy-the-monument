package me.kvdpxne.dtm.data.validation.context

import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.INVALID_REFERENCE
import me.kvdpxne.dtm.data.validation.context.extensions.validate

/**
 * @since 0.1.0
 */
fun isMonumentPositionValid(
  monumentPosition: RawMonumentPosition?
): Int {
  if (null == monumentPosition) {
    return INVALID_REFERENCE
  }

  val result: Int = monumentPosition.team.validate()
  if (EVERYTHING_OK != result) {
    return result
  }

  return EVERYTHING_OK
}