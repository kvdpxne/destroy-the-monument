package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.user.User

private val logger: KLogger = KotlinLogging.logger { }

class Team(val identity: Identity, var game: Game? = null) {

  /**
   * Collection of teammates belonging to the team.
   */
  val teammates: MutableCollection<Teammate> = mutableSetOf()

  /**
   *
   */
  fun findTeammate(user: User) = teammates.find {
    it.user == user
  }

  /**
   *
   */
  fun hasTeammate(user: User) = null != findTeammate(user)

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to add a teammate to a team, use the [Game.addTeammate]
   * method.
   */
  fun addTeammate(user: User) = addTeammate(user.toTeammate(identity, this))

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to add a teammate to a team, use the [Game.addTeammate]
   * method.
   */
  fun addTeammate(teammate: Teammate) = teammates.add(teammate).also {
    if (it) {
      logger.debug {
        "A new $teammate teammate has been added to the $this team."
      }
    }
  }

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to remove a teammate from a team, use the
   * [Game.removeTeammate] method.
   */
  fun removeTeammate(user: User) = findTeammate(user)?.let {
    removeTeammate(it)
  } ?: false

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to remove a teammate from a team, use the
   * [Game.removeTeammate] method.
   */
  fun removeTeammate(teammate: Teammate) = teammates.remove(teammate).also {
    if (it) {
      logger.debug {
        "Removed $teammate user from $this team."
      }
    }
  }

  /**
   *
   */
  fun size(): Int {
    return teammates.size
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