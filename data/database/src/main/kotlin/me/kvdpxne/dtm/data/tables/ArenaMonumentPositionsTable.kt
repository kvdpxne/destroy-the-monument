package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object ArenaMonumentPositionsTable : Table(En.ARENA_MONUMENT_POSITIONS) {

  /**
   * @since 0.1.0
   */
  val arenaIdentifier: Column<UUID> = this.uuid(Efn.ARENA_IDENTIFIER)
    .references(ArenaTable.identifier)

  /**
   * @since 0.1.0
   */
  val monumentPositionIdentifier: Column<UUID> = this.uuid(Efn.MONUMENT_POSITION_IDENTIFIER)
      .references(MonumentPositionTable.identifier)
}