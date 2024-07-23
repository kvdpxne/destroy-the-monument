package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TableProfession : Table<Nothing>("profession") {

  val name = varchar("name")
  val displayName = varchar("display_name")
  val cost = int("cost")
  val enabled = boolean("enabled")

  val identifier = varchar("identifier").primaryKey()
}