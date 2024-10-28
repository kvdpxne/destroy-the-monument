package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TeamTable : Table(En.TEAM) {

  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER).uniqueIndex()

  val name: Column<String> = this.varchar(Efn.NAME, 12)

  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}