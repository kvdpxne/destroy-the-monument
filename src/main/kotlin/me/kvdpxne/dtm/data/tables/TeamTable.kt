package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TeamTable : Table("team") {

  val identifier: Column<UUID> = this.uuid("identifier").uniqueIndex()

  val name: Column<String> = this.varchar("name", 12)

  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}