package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.shared.toPair
import me.kvdpxne.dtm.data.tables.UserWalletTable
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.main.validateUserWallet
import org.jetbrains.exposed.sql.ResultRow

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawUserWallet(
  identifier: UUID = this[UserWalletTable.identifier]
): Pair<RawUserWallet, ValidationResult> {
  val coins: Long = this[UserWalletTable.coins]
  val multiplier: Float = this[UserWalletTable.multiplier]
  val infinite: Boolean = this[UserWalletTable.infinite]
  val locked: Boolean = this[UserWalletTable.locked]

  return RawUserWallet(
    identifier,
    coins,
    multiplier,
    infinite,
    locked
  ).toPair(
    validateUserWallet(
      identifier,
      coins,
      multiplier,
      infinite,
      locked
    )
  )
}