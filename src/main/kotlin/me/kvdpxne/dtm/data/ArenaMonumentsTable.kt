package me.kvdpxne.dtm.data

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object ArenaMonumentsTable : Table<Nothing>("arena_monuments") {

  val arena = varchar("arena")
  val monument = varchar("monument")
}