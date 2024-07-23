package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object TableUser : Table<Nothing>("user") {

  //
  val name = varchar("name")

  //
  val statisticsIdentifier = varchar("statistics_identifier")
  val walletIdentifier = varchar("wallet_identifier")

  val profession = varchar("profession")

  //
  var identifier = varchar("identifier").primaryKey()
}