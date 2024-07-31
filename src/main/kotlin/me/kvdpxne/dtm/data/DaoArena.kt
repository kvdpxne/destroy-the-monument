package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableArena
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.ArenaMap
import me.kvdpxne.dtm.uid.toUuid
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.ktorm.support.sqlite.toLowerCase

/**
 * @since 0.1.0
 */
object DaoArena {

  /**
   * @since 0.1.0
   */
  private fun toArena(
    row: QueryRowSet
  ): Arena {
    //
    val identifier = row[TableArena.identifier]!!

    //
    val mapIdentifier = row[TableArena.mapIdentifier]
    val mapName = row[TableArena.mapName]

    //
    val fs1 = DaoArenaPositionMonument.findArenaPositionMonumentByArenaIdentifier(identifier)
    val fs2 = DaoArenaPositionRevival.findArenaPositionRevivalByArenaIdentifier(identifier)


    //
    val name = row[TableArena.name]!!

    //
    return Arena(
      name,
      identifier
    ).apply {
      fs1.forEach { this.addPositionMonument(it) }
      fs2.forEach { this.addRevivalPosition(it) }

      if (mapIdentifier == null || mapName == null) {
        return@apply
      }

      this.map = ArenaMap(
        mapName,
        mapIdentifier.toUuid()
      )
    }
  }

  /**
   * @since 0.1.0
   */
  fun findArenas(): List<Arena> {
    return database.from(TableArena)
      .select()
      .mapNotNull {
        toArena(it)
      }
      .toList()
  }

  /**
   * @since 0.1.0
   */
  fun findArenaByIdentifierOrNull(
    identifier: String
  ): Arena? {
    return database.from(TableArena)
      .select()
      .where {
        TableArena.identifier eq identifier
      }
      .map {
        toArena(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun findArenaByNameOrNull(
    name: String
  ): Arena? {
    return database.from(TableArena)
      .select()
      .where {
        TableArena.name.toLowerCase() eq name.lowercase()
      }
      .map {
        this.toArena(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertArena(
    arena: Arena
  ) {
    database.insert(TableArena) {
      set(it.identifier, arena.identifier)
      set(it.name, arena.name)
      set(it.mapIdentifier, arena.map?.identifier.toString())
      set(it.mapIdentifier, arena.map?.name)
    }
  }

  /**
   * @since 0.1.0
   */
  fun updateArena(arena: Arena) {
    database.update(TableArena) {
      set(it.name, arena.name)
      set(it.mapIdentifier, arena.map?.identifier.toString())
      set(it.mapName, arena.map?.name)

      where {
        it.identifier eq arena.identifier
      }
    }
  }
}