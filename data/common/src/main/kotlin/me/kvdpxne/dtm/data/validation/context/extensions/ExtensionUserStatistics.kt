package me.kvdpxne.dtm.data.validation.context.extensions

import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.validation.context.isUserStatisticsValid

/**
 * @since 0.1.0
 */
fun RawUserStatistics?.validate(): Int {
  return isUserStatisticsValid(this)
}