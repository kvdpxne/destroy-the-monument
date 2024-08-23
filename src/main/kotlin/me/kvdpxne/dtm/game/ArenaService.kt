package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.DaoArena
import me.kvdpxne.dtm.data.DaoArenaPositionMonument
import me.kvdpxne.dtm.data.DaoArenaPositionRevival
import me.kvdpxne.dtm.data.DaoPositionMonument
import me.kvdpxne.dtm.data.DaoPositionRevival

/**
 * @since 0.1.0
 */
object ArenaService {

  /**
   * @since 0.1.0
   */
  fun findArenas(): List<BaseArena> {
    return DaoArena.findArenas()
  }

  fun findArenaByIdentifier(
    identifier: String
  ): BaseArena? {
    return DaoArena.findArenaByIdentifierOrNull(identifier)
  }

  /**
   * @param name
   *
   * @since 0.1.0
   */
  fun findArenaByName(
    name: String,
  ): BaseArena? {
    return DaoArena.findArenaByNameOrNull(name)
  }

  /**
   * @since 0.1.0
   */
  fun insertArena(
    arena: BaseArena
  ) {
    DaoArena.insertArena(arena)
  }

  /**
   * @since 0.1.0
   */
  fun insertArenaRevivalPosition(
    arena: BaseArena,
    revivalPosition: RevivalPosition<Team>
  ) {
    DaoPositionRevival.insertPositionRevival(revivalPosition)
    DaoArenaPositionRevival.insertArenaPositionRevival(arena.identifier, revivalPosition.identifier)
  }

  /**
   * @since 0.1.0
   */
  fun insertArenaMonumentPosition(
    arena: BaseArena,
    monumentPosition: BaseMonumentPosition<Team>
  ) {
    DaoPositionMonument.insertPositionMonument(monumentPosition)
    DaoArenaPositionMonument.insertArenaPositionMonument(arena.identifier, monumentPosition.identifier)
  }
}