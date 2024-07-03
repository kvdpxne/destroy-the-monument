package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.shared.Identity
import me.kvdpxne.dtm.user.User

private val logger: KLogger = KotlinLogging.logger { }

class Team(
  val identity: Identity,
  var game: Game? = null
) : Communicative {

  /**
   * Collection of teammates belonging to the team.
   */
  val teammateMutableSet: MutableSet<Teammate>

  /**
   *
   */
  var health: Int
    private set

  /**
   *
   */
  init {
    this.teammateMutableSet = mutableSetOf()
    this.health = -1
  }

  /**
   *
   */
  fun findTeammate(user: User): Teammate? {
    return teammateMutableSet.find {
      it.user == user
    }
  }

  /**
   *
   */
  fun hasTeammate(user: User): Boolean {
    return null != findTeammate(user)
  }

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to add a teammate to a team, use the [Game.addTeammate]
   * method.
   */
  fun addTeammate(user: User): Boolean {
    return addTeammate(user.toTeammate(identity, this))
  }

  /**
   * This method is transient and should not be used directly on this object.
   * If you need to add a teammate to a team, use the [Game.addTeammate]
   * method.
   */
  fun addTeammate(teammate: Teammate): Boolean {
    return teammateMutableSet.add(teammate).also {
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
    return teammateMutableSet.remove(teammate).also {
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
  fun dealDamage(): Boolean {
    if (0 >= this.health) {
      return false
    }

    --this.health
    return true
  }

  /**
   * Returns an unsigned integer representing the total number of teammates
   * on the current team.
   *
   * @return The number of teammates on the team.
   * @since 0.1
   */
  fun size(): Int {
    return this.teammateMutableSet.size
  }

  /**
   * Alias for [Teammate.sendMessage]
   *
   * @since 0.1
   */
  override fun sendMessage(
    message: String
  ) {
    this.teammateMutableSet.forEach { teammate: Teammate ->
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
    if (this.teammateMutableSet.isEmpty()) {
      return
    }

    val body = message()
    this.teammateMutableSet.forEach { teammate: Teammate ->
      teammate.sendMessage(body)
    }
  }

  /**
   * Alias for [Teammate.sendMessages]
   *
   * @since 0.1
   */
  override fun sendMessages(
    vararg messageArray: String
  ) {
    if (this.teammateMutableSet.isEmpty() || messageArray.isEmpty()) {
      return
    }

    this.teammateMutableSet.forEach { teammate: Teammate ->
      teammate.sendMessages(*messageArray)
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