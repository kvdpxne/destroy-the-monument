package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import me.kvdpxne.dtm.data.VariableGuidelines
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableUser : Table(En.USER) {

  /**
   * @since 0.1.0
   */
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  val statisticsIdentifier: Column<UUID> = this.uuid(Efn.STATISTICS_IDENTIFIER)
    .uniqueIndex()
    .references(TableUserStatistics.identifier)

  /**
   * @since 0.1.0
   */
  val walletIdentifier: Column<UUID> = this.uuid(Efn.WALLET_IDENTIFIER)
    .uniqueIndex()
    .references(TableUserWallet.identifier)

  /**
   * @since 0.1.0
   */
  val name: Column<String> = this.varchar(Efn.NAME, VariableGuidelines.MAX_NAME_LENGTH)

  /**
   * @since 0.1.0
   */
  val displayName: Column<String?> = this.varchar(Efn.DISPLAY_NAME, VariableGuidelines.MAX_DISPLAY_NAME_LENGTH)
    .nullable()

  /**
   * @since 0.1.0
   */
  val profession: Column<String> = varchar(Efn.PROFESSION_NAME, 24)

  /**
   * @since 0.1.0
   */
  val locale: Column<String?> = char(Efn.LOCALE, 5)
    .nullable()

  /**
   * @since 0.1.0
   */
  override val primaryKey: PrimaryKey
    get() = PrimaryKey(this.identifier)

}