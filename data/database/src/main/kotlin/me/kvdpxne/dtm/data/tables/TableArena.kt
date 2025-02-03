package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableArena : Table(En.ARENA) {

  /**
   * @since 0.1.0
   */
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  val mapIdentifier: Column<UUID?> = this.uuid(Efn.ARENA_MAP_IDENTIFIER)
    .references(TableArenaMap.identifier)
    .nullable()

  /**
   * @since 0.1.0
   */
  val name: Column<String> = this.varchar(Efn.NAME, 24)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  override val primaryKey: PrimaryKey
    get() = PrimaryKey(this.identifier)
}