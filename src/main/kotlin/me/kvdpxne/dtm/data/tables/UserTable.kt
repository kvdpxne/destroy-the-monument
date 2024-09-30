package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object UserTable : Table("user") {

  //
  val identifier: Column<UUID> = this.uuid("identifier")
    .uniqueIndex()

  //
  val statisticsIdentifier: Column<UUID> = this.uuid("statistics_identifier")
    .uniqueIndex()
    .references(UserStatisticsTable.identifier)

  //
  val walletIdentifier: Column<UUID> = this.uuid("wallet_identifier")
    .uniqueIndex()
    .references(UserWalletTable.identifier)

  //
  val name: Column<String> = varchar("name", 16)
  val profession: Column<String> = varchar("profession", 24)

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}