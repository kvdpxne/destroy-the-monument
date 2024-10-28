package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object ArenaRevivalPositionsTable : Table(En.ARENA_REVIVAL_POSITIONS) {

  //
  val arenaIdentifier: Column<UUID> = this.uuid(Efn.ARENA_IDENTIFIER)
    .references(ArenaTable.identifier)

  //
  val revivalPositionIdentifier: Column<UUID> = this.uuid(Efn.REVIVAL_POSITION_IDENTIFIER)
    .references(RevivalPositionTable.identifier)
}