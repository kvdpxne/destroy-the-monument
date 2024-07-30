package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.float
import org.ktorm.schema.text

object TablePositionRevival : Table<Nothing>("position_revival") {

  val x = double("x")
  val y = double("y")
  val z = double("z")
  val pitch = float("pitch")
  val yaw = float("yaw")
  val teamIdentityIdentifier = text("team_identity_identifier")

  val identifier = text("identifier").primaryKey()
}