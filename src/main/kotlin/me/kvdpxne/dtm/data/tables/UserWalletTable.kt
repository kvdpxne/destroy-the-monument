package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object UserWalletTable : Table("user_wallet") {

  //
  val identifier: Column<UUID> = this.uuid("identifier").uniqueIndex()

  //
  val coins: Column<Long> = this.long("coins").default(0)
  val multiplier: Column<Float> = this.float("multiplier").default(1.0F)

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}