package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import me.kvdpxne.dtm.data.VariableGuidelines
import me.kvdpxne.dtm.data.extensions.usint64
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableUserWallet : Table(En.USER_WALLET) {

  /**
   * @since 0.1.0
   */
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex("${En.USER_WALLET}_${Efn.IDENTIFIER}")

  /**
   * @since 0.1.0
   */
  val coins: Column<Long> = this.usint64(Efn.COINS)

  /**
   * @since 0.1.0
   */
  val multiplier: Column<Float> = this.float(Efn.MULTIPLIER).apply {
    this.check {
      it.between(VariableGuidelines.MIN_MULTIPLIER, VariableGuidelines.MAX_MULTIPLIER)
    }
  }

  /**
   * @since 0.1.0
   */
  val infinite: Column<Boolean> = this.bool(Efn.INFINITE)

  /**
   * @since 0.1.0
   */
  val locked: Column<Boolean> = this.bool(Efn.LOCKED)

  /**
   * @since 0.1.0
   */
  override val primaryKey: PrimaryKey
    get() = PrimaryKey(this.identifier)
}