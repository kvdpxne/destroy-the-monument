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
  fun findArenas(): List<Arena> {
    return DaoArena.findArenas()
  }

  fun findArenaByIdentifier(
    identifier: String
  ): Arena? {
    return DaoArena.findArenaByIdentifierOrNull(identifier)
  }

  /**
   * @param name
   *
   * @since 0.1.0
   */
  fun findArenaByName(
    name: String,
  ): Arena? {
    return DaoArena.findArenaByNameOrNull(name)
  }

  /**
   * @since 0.1.0
   */
  fun insertArena(
    arena: Arena
  ) {
    DaoArena.insertArena(arena)
  }

  /**
   * @since 0.1.0
   */
  fun insertArenaRevivalPosition(
    arena: Arena,
    revivalPosition: RevivalPosition
  ) {
    DaoPositionRevival.insertPositionRevival(revivalPosition)
    DaoArenaPositionRevival.insertArenaPositionRevival(arena.identifier, revivalPosition.identifier)
  }

  /**
   * @since 0.1.0
   */
  fun insertArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPosition
  ) {
    DaoPositionMonument.insertPositionMonument(monumentPosition)
    DaoArenaPositionMonument.insertArenaPositionMonument(arena.identifier, monumentPosition.identifier)
  }
}