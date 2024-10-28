package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object UserWalletTable : Table(En.USER_WALLET) {

  //
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER).uniqueIndex()

  //
  val coins: Column<Long> = this.long(Efn.COINS).default(0)
  val multiplier: Column<Float> = this.float(Efn.MULTIPLIER).default(1.0F)

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}