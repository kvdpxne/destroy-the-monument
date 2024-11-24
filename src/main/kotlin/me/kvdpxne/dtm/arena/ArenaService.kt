package me.kvdpxne.dtm.arena

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.arena.map.ArenaMap
import me.kvdpxne.dtm.data.daos.ArenaDao
import me.kvdpxne.dtm.data.daos.ArenaMonumentPositionsDao
import me.kvdpxne.dtm.data.daos.ArenaRevivalPositionsDao
import me.kvdpxne.dtm.data.daos.MonumentPositionDao
import me.kvdpxne.dtm.data.daos.RevivalPositionDao
import me.kvdpxne.dtm.position.monument.MonumentPosition
import me.kvdpxne.dtm.position.monument.MonumentPositionImpl
import me.kvdpxne.dtm.position.revival.RevivalPosition
import me.kvdpxne.dtm.shared.ArenaUuid
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.team.Team

/**
 * Provides services for managing arena-related operations, including
 * database interactions.
 *
 * @since 0.1.0
 */
object ArenaService {

  /**
   * Retrieves all arenas from the database.
   *
   * @return a list of all arenas.
   * @since 0.1.0
   */
  fun findArenas(): List<Arena> {
    return runBlocking {
      ArenaDao.findArenas()
    }
  }

  /**
   * Finds an arena by its unique identifier.
   *
   * @param identifier the unique arena identifier.
   * @return the corresponding arena, or `null` if not found.
   * @since 0.1.0
   */
  fun findArenaByIdentifierOrNull(
    identifier: ArenaUuid
  ): Arena? {
    return runBlocking {
      ArenaDao.findArenaByIdentifier(identifier)
    }
  }

  /**
   * Finds an arena by its name.
   *
   * @param name the name of the arena.
   * @param ignoreCase whether the search should ignore case.
   * @return the corresponding arena, or `null` if not found.
   * @since 0.1.0
   */
  fun findArenaByNameOrNull(
    name: String,
    ignoreCase: Boolean = true
  ): Arena? {
    return runBlocking {
      ArenaDao.findArenaByName(name, ignoreCase)
    }
  }

  /**
   * Inserts a new arena into the database.
   *
   * @param arena the arena to insert.
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

  /**
   * Updates the map associated with an arena.
   *
   * @param arena the arena to update.
   * @param map the new map for the arena.
   * @since 0.1.0
   */
  fun updateArenaMap(arena: Arena, map: ArenaMap) {
    runBlocking {
      ArenaDao.updateArenaMap(arena, map)
    }
  }

  /**
   * Inserts a revival position into an arena.
   *
   * @param arena the arena to update.
   * @param revivalPosition the revival position to insert.
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
   * Inserts a monument position into an arena.
   *
   * @param arena the arena to update.
   * @param monumentPosition the monument position to insert.
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
   * Deletes a monument position from an arena.
   *
   * @param arena the arena to update.
   * @param monumentPosition the monument position to delete.
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

  /**
   * Deletes an arena by its unique identifier.
   *
   * @param identifier the unique arena identifier.
   * @return `true` if the arena was deleted, `false` otherwise.
   * @since 0.1.0
   */
  fun deleteArenaByIdentifier(
    identifier: ArenaUuid
  ): Boolean {
    val arena: Arena = this.findArenaByIdentifierOrNull(identifier)
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