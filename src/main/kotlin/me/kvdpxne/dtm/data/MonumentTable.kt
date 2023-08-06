package me.kvdpxne.dtm.data

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object MonumentTable : Table<Nothing>("monument") {

  val team = varchar("team")
  val x = int("x")
  val y = int("y")
  val z = int("z")

  val identifier = varchar("identifier").primaryKey()
}