package me.kvdpxne.dtm.arena.voting

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.user.LocalUser

/**
 * @param identifier
 * @param arena
 * @param initialSize
 *
 * @since 0.1.0
 */
internal class ArenaVotingImpl internal constructor(
  // @formatter:off
  override val identifier : Int,
  override val arena      : Arena,
               initialSize: Int = 16,
  // @formatter:on
) : ArenaVoting {

  /**
   * @since 0.1.0
   */
  private val _voters: MutableSet<LocalUser> =
    HashSet(initialSize)

  override val voters: Collection<LocalUser>
    get() = this._voters.toList()

  override val votes: Int
    get() = this._voters.size

  override fun hasVote(
    user: LocalUser
  ): Boolean {
    return !this._voters.contains(user)
  }

  override fun castVote(
    user: LocalUser
  ): Boolean {
    return this._voters.add(user)
  }

  override fun revokeVote(
    user: LocalUser
  ): Boolean {
    return this._voters.remove(user)
  }

  override fun revokeVotes() {
    this._voters.clear()
  }

  /**
   * @since 0.1.0
   */
  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as ArenaVotingImpl
    return this.arena == other.arena
  }

  /**
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.arena.hashCode()
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder().begin("ArenaVoting")
      .add("arena", this.arena)
      .add("voters", this._voters)
      .add("votes", this._voters.size)
      .add("identifier", this.identifier)
      .build()
  }
}