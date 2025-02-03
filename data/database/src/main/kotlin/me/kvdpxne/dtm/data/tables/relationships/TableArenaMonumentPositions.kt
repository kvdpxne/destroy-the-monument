package me.kvdpxne.dtm.data.tables.relationships

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import me.kvdpxne.dtm.data.tables.TableArena
import me.kvdpxne.dtm.data.tables.TableMonumentPosition
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableArenaMonumentPositions : Table(En.ARENA_MONUMENT_POSITIONS) {

  /**
   * @since 0.1.0
   */
  val arenaIdentifier: Column<UUID> = this.uuid(Efn.ARENA_IDENTIFIER)
    .references(TableArena.identifier)

  /**
   * @since 0.1.0
   */
  val monumentPositionIdentifier: Column<UUID> = this.uuid(Efn.MONUMENT_POSITION_IDENTIFIER)
      .references(TableMonumentPosition.identifier)
}