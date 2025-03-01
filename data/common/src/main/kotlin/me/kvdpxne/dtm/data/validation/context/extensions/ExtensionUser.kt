package me.kvdpxne.dtm.data.validation.context.extensions

import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.validation.context.isUserValid

/**
 * @since 0.1.0
 */
fun RawUser?.validate(): Int {
  return isUserValid(this)
}