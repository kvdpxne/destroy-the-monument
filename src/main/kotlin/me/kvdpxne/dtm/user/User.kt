package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.statistics.Statistics
import java.util.*

class User(
  val identifier: UUID,
  var name: String,
  var statistics: Statistics = Statistics.empty()
) {

  val performer: Performer

  init {
    performer = UserPerformer(identifier, name, this)
  }
}