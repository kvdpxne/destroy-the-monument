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

  // Unikalny identyfikator, który reprezentuje rząd obiektu areny.
  val arenaIdentifier: Column<UUID> = this.uuid(Efn.ARENA_IDENTIFIER)
    .references(ArenaTable.identifier)

  // Unikalny identyfikator, który reprezentuje rząd obiektu "Monument"
  val monumentPositionIdentifier: Column<UUID> = this.uuid(Efn.MONUMENT_POSITION_IDENTIFIER)
    .references(MonumentPositionTable.identifier)
}