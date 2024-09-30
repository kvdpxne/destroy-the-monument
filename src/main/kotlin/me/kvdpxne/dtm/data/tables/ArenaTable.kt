package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object ArenaTable : Table("arena") {

  val identifier: Column<UUID> = this.uuid("identifier").uniqueIndex()

  val name: Column<String> = this.text("name")

  val mapIdentifier: Column<UUID?> = this.uuid("map_identifier").nullable()
  val mapName: Column<String?> = this.text("map_name").nullable()

  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}