package me.kvdpxne.dtm.data.tables.relationships

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import me.kvdpxne.dtm.data.tables.TableArena
import me.kvdpxne.dtm.data.tables.TableRevivalPosition
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableArenaRevivalPositions : Table(En.ARENA_REVIVAL_POSITIONS) {

  /**
   * @since 0.1.0
   */
  val arenaIdentifier: Column<UUID> = this.uuid(Efn.ARENA_IDENTIFIER)
    .references(TableArena.identifier)

  /**
   * @since 0.1.0
   */
  val revivalPositionIdentifier: Column<UUID> = this.uuid(Efn.REVIVAL_POSITION_IDENTIFIER)
    .references(TableRevivalPosition.identifier)
}