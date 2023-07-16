package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.statistics.Statistics
import java.util.*

class User(
  val identifier: UUID,
  var name: String,
  var statistics: Statistics = Statistics.empty()
)