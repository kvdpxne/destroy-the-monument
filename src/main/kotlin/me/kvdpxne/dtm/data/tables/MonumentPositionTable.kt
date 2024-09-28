package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object MonumentPositionTable : Table("monument_position") {

  //
  val identifier: Column<UUID> = this.uuid("identifier")
    .uniqueIndex()

  //
  val teamIdentifier: Column<UUID> = this.uuid("team_identifier")
    .uniqueIndex()
    .references(TeamTable.identifier)

  //
  val x: Column<Int> = this.integer("x")
  val y: Column<Int> = this.integer("y")
  val z: Column<Int> = this.integer("z")

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}