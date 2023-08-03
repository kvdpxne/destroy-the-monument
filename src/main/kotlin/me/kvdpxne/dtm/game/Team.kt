package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.user.User

class Team(val identity: Identity, val game: Game) {

  val teammates = mutableSetOf<Teammate>()

  companion object {

    private val logger: KLogger = KotlinLogging.logger { }
  }

  fun hasTeammate(user: User) = null != teammates.find {
    it.user == user
  }

  /**
   *
   */
  fun addTeammate(user: User) = addTeammate(user.toTeammate(identity, this))

  /**
   * Tries to add the given [teammate] to the [teammates] collection if the
   * given [teammate] is not currently present in the [teammates] collection.
   *
   * @see Game.addTeam
   * @see Game.addTeammate
   */
  fun addTeammate(teammate: Teammate) = teammates.add(teammate).also {
    if (it) {
      logger.debug {
        "A new $teammate teammate has been added to the $this team."
      }
    }
  }

  fun removeTeammate(user: User) = teammates.find {
    it.user == user
  }?.let {
    //
    removeTeammate(it)
  } ?: false

  /**
   *
   *
   * @see Game.removeTeam
   * @see Game.removeTeammate
   */
  fun removeTeammate(teammate: Teammate) = teammates.remove(teammate).also {
    if (it) {
      logger.debug {
        "Removed $teammate user from $this team."
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Team

    if (identity != other.identity) return false
    if (game != other.game) return false

    return true
  }

  override fun hashCode(): Int {
    var result = identity.hashCode()
    result = 31 * result + game.hashCode()
    return result
  }

  override fun toString(): String {
    return "Team(identity='$identity', in='$game')"
  }
}