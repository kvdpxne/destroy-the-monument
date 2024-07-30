package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.text

object TableGame : Table<Nothing>("game") {

  val name = text("name")

  val identifier = text("identifier").primaryKey()
}