package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object GameArenasTable : Table(En.GAME_ARENAS) {

  val gameIdentifier: Column<UUID> = this.uuid(Efn.GAME_IDENTIFIER)
    .references(GameTable.identifier)

  val arenaIdentifier: Column<UUID> = this.uuid(Efn.ARENA_IDENTIFIER)
    .references(ArenaTable.identifier)
}