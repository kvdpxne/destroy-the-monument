package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.shared.ancillary.Nameable

/**
 * @since 0.1.0
 */
interface Game<T : Team> : Identifiable<String>, Nameable {

  /**
   * @since 0.1.0
   */
  val teams: Collection<T>

  /**
   * @since 0.1.0
   */
  val arenas: Collection<Arena>

  /**
   * @since 0.1.0
   */
  val numberOfTeams: Int

  /**
   * @since 0.1.0
   */
  val numberOfArenas: Int

  /**
   * @since 0.1.0
   */
  val isLocal: Boolean
    get() = false

  /**
   * @since 0.1.0
   */
  fun hasTeam(team: T): Boolean

  /**
   * @since 0.1.0
   */
  fun hasArena(arena: Arena): Boolean

  /**
   * @since 0.1.0
   */
  fun findTeamByIdentifier(identifier: String): T?

  /**
   * @since 0.1.0
   */
  fun findArenaByIdentifier(identifier: String): Arena?

  /**
   * @since 0.1.0
   */
  fun toLocalGame(): LocalGame
}