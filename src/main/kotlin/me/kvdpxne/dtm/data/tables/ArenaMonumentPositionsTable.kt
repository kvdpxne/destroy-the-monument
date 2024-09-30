package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object ArenaMonumentPositionsTable : Table("arena_monument_positions") {

  // Unikalny identyfikator, który reprezentuje rząd obiektu areny.
  val arenaIdentifier: Column<UUID> = this.uuid("arena_identifier")
    .references(ArenaTable.identifier)

  // Unikalny identyfikator, który reprezentuje rząd obiektu "Monument"
  val monumentPositionIdentifier: Column<UUID> = this.uuid("monument_position_identifier")
    .references(MonumentPositionTable.identifier)
}