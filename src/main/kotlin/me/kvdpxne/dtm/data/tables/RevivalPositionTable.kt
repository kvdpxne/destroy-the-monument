package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object RevivalPositionTable : Table("revival_position") {

  //
  val identifier: Column<UUID> = this.uuid("identifier")
    .uniqueIndex()

  //
  val teamIdentifier: Column<UUID> = this.uuid("team_identifier")
    .uniqueIndex()
    .references(TeamTable.identifier)

  //
  val x: Column<Double> = this.double("x")
  val y: Column<Double> = this.double("y")
  val z: Column<Double> = this.double("z")
  val pitch: Column<Float> = this.float("pitch")
  val yaw: Column<Float> = this.float("yaw")

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}