package me.kvdpxne.dtm.data.validation.context.extensions

import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.validation.context.isMonumentPositionValid

/**
 * @since 0.1.0
 */
fun RawMonumentPosition?.validate(): Int {
  return isMonumentPositionValid(this)
}