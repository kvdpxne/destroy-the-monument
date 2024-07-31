package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text

object TablePositionMonument : Table<Nothing>("position_monument") {

  val x = int("x")
  val y = int("y")
  val z = int("z")
  val teamIdentifier = text("team_identifier")

  val identifier = text("identifier").primaryKey()
}