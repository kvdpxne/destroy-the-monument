package me.kvdpxne.dtm.data

import org.ktorm.schema.*

object SpawnPointTable : Table<Nothing>("spawn_point") {

  val team = varchar("team")
  val x = double("x")
  val y = double("y")
  val z = double("z")
  val pitch = float("pitch")
  val yaw = float("yaw")

  val identifier = varchar("identifier").primaryKey()
}