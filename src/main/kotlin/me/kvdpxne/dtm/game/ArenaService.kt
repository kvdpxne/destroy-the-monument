package me.kvdpxne.dtm.game

import java.util.UUID
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ArenaDao
import me.kvdpxne.dtm.data.ArenaMonumentPositionsDao
import me.kvdpxne.dtm.data.ArenaRevivalPositionsDao
import me.kvdpxne.dtm.data.MonumentPositionDao
import me.kvdpxne.dtm.data.RevivalPositionDao

/**
 * @since 0.1.0
 */
object ArenaService {

  /**
   * @since 0.1.0
   */
  fun findArenas(): List<Arena> {
    return runBlocking {
      ArenaDao.findArenas()
    }
  }

  /**
   * @since 0.1.0
   */
  fun findArenaByIdentifier(
    identifier: UUID
  ): Arena? {
    return runBlocking {
      ArenaDao.findArenaByIdentifier(identifier)
    }
  }

  /**
   * @param name
   *
   * @since 0.1.0
   */
  fun findArenaByName(
    name: String,
    ignoreCase: Boolean = true
  ): Arena? {
    return runBlocking {
      ArenaDao.findArenaByName(name, ignoreCase)
    }
  }

  /**
   * @since 0.1.0
   */
  fun insertArena(
    arena: Arena
  ) {
    runBlocking {
      ArenaDao.insertArena(arena)
    }
  }

  fun updateArenaMap(arena: Arena, map: ArenaMap) {
    runBlocking {
      ArenaDao.updateArenaMap(arena, map)
    }
  }

  /**
   * @since 0.1.0
   */
  fun insertArenaRevivalPosition(
    arena: Arena,
    revivalPosition: RevivalPosition<Team>
  ) {
    runBlocking {
      RevivalPositionDao.insertRevivalPosition(revivalPosition)
      ArenaRevivalPositionsDao.insertArenaRevivalPosition(arena, revivalPosition)
    }

  }

  /**
   * @since 0.1.0
   */
  fun insertArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPositionImpl<Team>
  ) {
    runBlocking {
      MonumentPositionDao.insertMonumentPosition(monumentPosition)
      ArenaMonumentPositionsDao.insertArenaMonumentPosition(arena, monumentPosition)
    }
  }
}