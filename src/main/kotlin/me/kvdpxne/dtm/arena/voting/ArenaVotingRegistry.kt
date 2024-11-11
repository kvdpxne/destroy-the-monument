package me.kvdpxne.dtm.arena.voting

import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.user.LocalUser

/**
 * Interface defining a registry for arena voting instances, allowing for
 * management of multiple arenas in the voting system.
 *
 * @since 0.1.0
 */
interface ArenaVotingRegistry {

  /**
   * A collection of all arenas currently registered for voting.
   *
   * @since 0.1.0
   */
  val arenas: Collection<ArenaVoting>

  /**
   * The total number of arenas registered in the voting system.
   *
   * @since 0.1.0
   */
  val size: Int

  val totalVotes: Int

  /**
   * @param arena
   *
   * @since 0.1.0
   */
  fun contains(arena: Arena): Boolean

  fun castVote(identifier: Int, user: LocalUser): Boolean

  fun revokeVote(identifier: Int, user: LocalUser): Boolean

  fun revokeVotes(identifier: Int)

  /**
   * Adds an arena to the registry, making it available for voting.
   *
   * @param arena The arena to add to the voting registry.
   * @return `true` if the arena was successfully added, `false` if it was already present.
   * @since 0.1.0
   */
  fun addArena(arena: Arena): Int

  /**
   * @since 0.1.0
   */
  fun removeArenaByIdentifier(identifier: Int): Boolean

  /**
   * Removes an arena from the registry, making it unavailable for voting.
   *
   * @param arena The arena to remove from the voting registry.
   * @return `true` if the arena was successfully removed, `false` if it was not found.
   * @since 0.1.0
   */
  fun removeArena(arena: Arena): Boolean

  /**
   * Clears all arenas from the voting registry, removing all from the voting system.
   *
   * @since 0.1.0
   */
  fun removeArenas()
}