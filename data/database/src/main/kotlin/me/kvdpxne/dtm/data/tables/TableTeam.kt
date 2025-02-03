package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableTeam : Table(En.TEAM) {

  /**
   * @since 0.1.0
   */
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  val name: Column<String> = this.varchar(Efn.NAME, 16)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  val displayName: Column<String?> = this.varchar(Efn.DISPLAY_NAME, 24)
    .nullable()

  /**
   * @since 0.1.0
   */
  override val primaryKey: PrimaryKey
    get() = PrimaryKey(this.identifier)
}