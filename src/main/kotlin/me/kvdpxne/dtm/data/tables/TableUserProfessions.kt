package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object TableUserProfessions : Table<Nothing>("user_professions") {

  val userIdentifier = varchar("user_identifier")
  val professionsIdentifier = varchar("professions_identifier")
}