package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object UserTable : Table(En.USER) {

  //
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  //
  val statisticsIdentifier: Column<UUID> = this.uuid(Efn.STATISTICS_IDENTIFIER)
    .uniqueIndex()
    .references(UserStatisticsTable.identifier)

  //
  val walletIdentifier: Column<UUID> = this.uuid(Efn.WALLET_IDENTIFIER)
    .uniqueIndex()
    .references(UserWalletTable.identifier)

  //
  val name: Column<String> = varchar(Efn.NAME, 16)
  val profession: Column<String> = varchar(Efn.PROFESSION_NAME, 24)
  val locale: Column<String?> = char(Efn.LOCALE, 5).nullable()

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}