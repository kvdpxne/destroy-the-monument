package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object TableUser : Table<Nothing>("user") {

  //
  val name = text("name")

  //
  val statisticsIdentifier = text("statistics_identifier")
  val walletIdentifier = text("wallet_identifier")

  val profession = varchar("profession")

  //
  var identifier = varchar("identifier").primaryKey()
}