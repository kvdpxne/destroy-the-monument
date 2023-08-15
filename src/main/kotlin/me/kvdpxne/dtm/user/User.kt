package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.statistics.Statistics
import java.util.*
import me.kvdpxne.dtm.profession.Profession

class User(
  val identifier: UUID,
  var name: String,
  var statistics: Statistics = Statistics.empty()
) {

  val performer: Performer
  var profession: Profession? = null

  init {
    performer = UserPerformer(identifier, name, this)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as User

    if (identifier != other.identifier) return false
    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    var result = identifier.hashCode()
    result = 31 * result + name.hashCode()
    return result
  }

  override fun toString(): String {
    return "User(identifier=$identifier, name='$name')"
  }
}