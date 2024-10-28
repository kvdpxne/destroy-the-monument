package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object RevivalPositionTable : Table(En.REVIVAL_POSITION) {

  //
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  //
  val teamIdentifier: Column<UUID> = this.uuid(Efn.TEAM_IDENTIFIER)
    .uniqueIndex()
    .references(TeamTable.identifier)

  //
  val x: Column<Double> = this.double(Efn.X)
  val y: Column<Double> = this.double(Efn.Y)
  val z: Column<Double> = this.double(Efn.Z)
  val pitch: Column<Float> = this.float(Efn.PITCH)
  val yaw: Column<Float> = this.float(Efn.YAW)

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}