package me.kvdpxne.dtm.arena

import java.util.UUID
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ArenaDao
import me.kvdpxne.dtm.data.ArenaMonumentPositionsDao
import me.kvdpxne.dtm.data.ArenaRevivalPositionsDao
import me.kvdpxne.dtm.data.MonumentPositionDao
import me.kvdpxne.dtm.data.RevivalPositionDao
import me.kvdpxne.dtm.position.MonumentPosition
import me.kvdpxne.dtm.position.MonumentPositionImpl
import me.kvdpxne.dtm.position.RevivalPosition
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.team.Team

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
    val insertedRows: Int = runBlocking {
      ArenaDao.insertArena(arena)
    }

    if (1 == insertedRows) {
      Debug.log {
        "Arena $arena has been inserted into the database."
      }
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

  /**
   * @since 0.1.0
   */
  fun deleteArenaMonumentPosition(
    arena: Arena,
    monumentPosition: MonumentPosition<Team>
  ) {
    runBlocking {
      ArenaMonumentPositionsDao.deleteArenaMonumentPosition(arena, monumentPosition)
      MonumentPositionDao.deleteMonumentPositionByIdentifier(monumentPosition.identifier)
    }
  }

  fun deleteArenaByIdentifier(
    identifier: UUID
  ): Boolean {
    val arena: Arena = this.findArenaByIdentifier(identifier)
      ?: return false

    runBlocking {
      launch {
        ArenaMonumentPositionsDao.deleteArenaMonumentPositions(arena)

        for (monumentPosition in arena.monumentPositions) {
          MonumentPositionDao.deleteMonumentPositionByIdentifier(monumentPosition.identifier)
        }
      }

      launch {
        ArenaRevivalPositionsDao.deleteArenaRevivalPositions(arena)

        for (revivalPosition in arena.revivalPositions) {
          RevivalPositionDao.deleteRevivalPositionByIdentifier(revivalPosition.identifier)
        }
      }
    }

    return true
  }
}