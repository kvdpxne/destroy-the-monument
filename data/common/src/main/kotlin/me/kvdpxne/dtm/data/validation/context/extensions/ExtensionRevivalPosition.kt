package me.kvdpxne.dtm.data.validation.context.extensions

import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.validation.context.isRevivalPositionValid

/**
 * @since 0.1.0
 */
fun RawRevivalPosition?.validate(): Int {
  return isRevivalPositionValid(this)
}