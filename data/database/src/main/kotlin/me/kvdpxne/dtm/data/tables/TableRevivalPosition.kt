package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableRevivalPosition : Table(En.REVIVAL_POSITION) {

  /**
   * @since 0.1.0
   */
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  val teamIdentifier: Column<UUID> = this.uuid(Efn.TEAM_IDENTIFIER)
    .uniqueIndex()
    .references(TableTeam.identifier)

  /**
   * @since 0.1.0
   */
  val x: Column<Double> = this.double(Efn.X)

  /**
   * @since 0.1.0
   */
  val y: Column<Double> = this.double(Efn.Y)

  /**
   * @since 0.1.0
   */
  val z: Column<Double> = this.double(Efn.Z)

  /**
   * @since 0.1.0
   */
  val pitch: Column<Float> = this.float(Efn.PITCH)

  /**
   * @since 0.1.0
   */
  val yaw: Column<Float> = this.float(Efn.YAW)

  /**
   * @since 0.1.0
   */
  override val primaryKey: PrimaryKey
    get() = PrimaryKey(this.identifier)
}