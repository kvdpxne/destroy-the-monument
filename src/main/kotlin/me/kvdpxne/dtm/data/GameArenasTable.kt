package me.kvdpxne.dtm.data

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object GameArenasTable : Table<Nothing>("game_arenas") {

  var game = varchar("game")
  val arena = varchar("arena")
}