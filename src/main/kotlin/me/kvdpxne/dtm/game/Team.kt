package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.shared.IdentifiableByName
import me.kvdpxne.dtm.user.User

class Team(val name: IdentifiableByName) {

  val teammates = mutableListOf<Teammate>()

  companion object {

    private val logger: KLogger = KotlinLogging.logger { }
  }

  fun hasTeammate(user: User) = null != teammates.find {
    it.user == user
  }

  fun addTeammate(user: User) {

  }

  fun removeTeammate(user: User) = teammates.removeIf {
    it.user == user
  }.also {
    if (it) {
      logger.debug {
        "Removed $user user from $this team."
      }
    }
  }

}