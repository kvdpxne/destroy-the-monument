package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.float
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TableUserWallet : Table<Nothing>("user_wallet") {

  val coins = int("coins")
  val multiplier = float("multiplier")

  val identifier = varchar("identifier").primaryKey()
}