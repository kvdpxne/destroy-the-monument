package me.kvdpxne.dtm.arena.voting

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.user.LocalUser

/**
 * Interface defining voting functionality for an arena, allowing users to cast
 * and revoke votes. Tracks the arena being voted on, current voters, and total vote count.
 *
 * @since 0.1.0
 */
interface ArenaVoting : Identifiable<Int> {

  /**
   * The arena associated with this voting instance.
   *
   * @since 0.1.0
   */
  val arena: Arena

  /**
   * A collection of users who have voted for this arena.
   *
   * @since 0.1.0
   */
  val voters: Collection<LocalUser>

  /**
   * The total number of votes cast for this arena.
   *
   * @since 0.1.0
   */
  val votes: Int

  /**
   * Checks if a specific user has already voted for this arena.
   *
   * @param user The user whose voting status is being checked.
   * @return `true` if the user has voted, `false` otherwise.
   * @since 0.1.0
   */
  fun hasVote(user: LocalUser): Boolean

  /**
   * Casts a vote for this arena from a specific user.
   *
   * @param user The user casting the vote.
   * @return `true` if the vote was successfully cast, `false` if the user had already voted.
   * @since 0.1.0
   */
  fun castVote(user: LocalUser): Boolean

  /**
   * Revokes a user's vote for this arena.
   *
   * @param user The user revoking their vote.
   * @return `true` if the vote was successfully revoked, `false` if the user had not voted.
   * @since 0.1.0
   */
  fun revokeVote(user: LocalUser): Boolean

  /**
   * Clears all votes for this arena, removing all users from the voter list.
   *
   * @since 0.1.0
   */
  fun revokeVotes()
}