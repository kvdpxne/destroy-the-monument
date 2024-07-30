package me.kvdpxne.dtm.game.temporary

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.game.TeamIdentity
import me.kvdpxne.dtm.user.User

private val logger: KLogger = KotlinLogging.logger { }

class Team(
  val identity: TeamIdentity,
  var game: Game? = null
) : Communicative {

  /**
   * Collection of teammates belonging to the team.
   */
  val _teammates: MutableSet<Teammate> = mutableSetOf()

  /**
   *
   */
  var health: Int = -1
    internal set

  /**
   * @since 0.1.0
   */
  val teammates: Array<Teammate>
    get() = this._teammates.toTypedArray()

  /**
   * Returns an unsigned integer representing the total number of teammates
   * on the current team.
   *
   * @return The number of teammates on the team.
   * @since 0.1
   */
  val size: Int
    get() = this._teammates.size

  /**
   *
   */
  fun hasTeammate(
    user: User
  ): Boolean {
    return this._teammates.any {
      it.user == user
    }
  }

  /**
   * @since 0.1.0
   */
  fun findTeammate(
    user: User
  ): Teammate? {
    return this._teammates.find {
      it.user == user
    }
  }

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to add a teammate to a team, use the [Game.addTeammate]
   * method.
   */
  fun addTeammate(teammate: Teammate): Boolean {
    return _teammates.add(teammate).also {
      if (it) {
        logger.debug {
          "A new $teammate teammate has been added to the $this team."
        }
      }
    }
  }

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to remove a teammate from a team, use the
   * [Game.removeTeammate] method.
   */
  fun removeTeammate(user: User): Boolean {
    return findTeammate(user)?.let {
      removeTeammate(it)
    } ?: false
  }

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to remove a teammate from a team, use the
   * [Game.removeTeammate] method.
   */
  fun removeTeammate(teammate: Teammate): Boolean {
    return _teammates.remove(teammate).also {
      if (it) {
        logger.debug {
          "Removed $teammate user from $this team."
        }
      }
    }
  }

  /**
   *
   */
  fun removeTeammates() {
    this._teammates.clear()
  }

  /**
   *
   */
  fun dealDamage(): Boolean {
    if (0 >= this.health) {
      return false
    }

    --this.health
    return true
  }

  /**
   * Alias for [Teammate.sendMessage]
   *
   * @since 0.1
   */
  override fun sendMessage(
    message: String
  ) {
    this._teammates.forEach { teammate: Teammate ->
      teammate.sendMessage(message)
    }
  }

  /**
   * Alias for [Teammate.sendMessage]
   *
   * @since 0.1
   */
  override fun sendMessage(
    message: () -> String
  ) {
    if (this._teammates.isEmpty()) {
      return
    }

    val body = message()
    this._teammates.forEach { teammate: Teammate ->
      teammate.sendMessage(body)
    }
  }

  /**
   * Alias for [Teammate.sendMessages]
   *
   * @since 0.1
   */
  override fun sendMessages(
    vararg messages: String
  ) {
    if (this._teammates.isEmpty() || messages.isEmpty()) {
      return
    }

    this._teammates.forEach { teammate: Teammate ->
      teammate.sendMessages(*messages)
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