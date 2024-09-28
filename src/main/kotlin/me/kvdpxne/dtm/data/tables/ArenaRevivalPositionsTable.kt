package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object ArenaRevivalPositionsTable : Table("arena_revival_positions") {

  //
  val arenaIdentifier: Column<UUID> = this.uuid("arena_identifier")
    .references(ArenaTable.identifier)

  //
  val revivalPositionIdentifier: Column<UUID> = this.uuid("revival_position_identifier")
    .references(RevivalPositionTable.identifier)
}