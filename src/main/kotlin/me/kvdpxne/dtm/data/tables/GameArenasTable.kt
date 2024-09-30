package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object GameArenasTable : Table("game_arenas") {

  val gameIdentifier: Column<UUID> = this.uuid("game_identifier")
    .references(GameTable.identifier)

  val arenaIdentifier: Column<UUID> = this.uuid("arena_identifier")
    .references(ArenaTable.identifier)
}