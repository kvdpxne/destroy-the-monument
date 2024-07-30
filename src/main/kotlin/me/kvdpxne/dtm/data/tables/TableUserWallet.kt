package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.float
import org.ktorm.schema.long
import org.ktorm.schema.text

object TableUserWallet : Table<Nothing>("user_wallet") {

  val coins = long("coins")
  val multiplier = float("multiplier")

  val identifier = text("identifier").primaryKey()
}