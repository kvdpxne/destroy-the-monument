package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.shared.ancillary.Nameable
import me.kvdpxne.dtm.team.Team

/**
 * @since 0.1.0
 */
interface Game<T : Team> : Identifiable<UUID>, Nameable {

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
  fun findTeamByIdentifier(identifier: UUID): T?

  /**
   * @since 0.1.0
   */
  fun findArenaByIdentifier(identifier: UUID): Arena?

  /**
   * @since 0.1.0
   */
  fun toLocalGame(): LocalGame
}