package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object MonumentPositionTable : Table(En.MONUMENT_POSITION) {

  //
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  //
  val teamIdentifier: Column<UUID> = this.uuid(Efn.TEAM_IDENTIFIER)
    .uniqueIndex()
    .references(TeamTable.identifier)

  //
  val x: Column<Int> = this.integer(Efn.X)
  val y: Column<Int> = this.integer(Efn.Y)
  val z: Column<Int> = this.integer(Efn.Z)

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}