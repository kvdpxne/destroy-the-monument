package me.kvdpxne.dtm.data.extensions.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.tables.TableUserWallet
import me.kvdpxne.dtm.data.validation.context.isUserWalletCoinsValid
import me.kvdpxne.dtm.data.validation.context.isUserWalletMultiplierValid
import me.kvdpxne.dtm.shared.toSingleLines
import org.jetbrains.exposed.sql.ResultRow

/**
 * @param identifier
 *
 * @throws IllegalArgumentException
 * @throws IllegalStateException
 *
 * @since 0.1.0
 */
internal fun ResultRow.toRawUserWallet(
  identifier: UUID
): RawUserWallet {
  val coins: Long = this[TableUserWallet.coins]
  check(isUserWalletCoinsValid(coins)) {
    """
      The database returned an invalid coin count of "$coins" for the wallet
      of a user with the identifier "$identifier".
    """.toSingleLines()
  }

  val multiplier: Float = this[TableUserWallet.multiplier]
  check(isUserWalletMultiplierValid(multiplier)) {
    """
      The database returned an invalid multiplier "$multiplier" for the wallet
      of the user with the identifier "$identifier".
    """.trimIndent()
  }

  val infinite: Boolean = this[TableUserWallet.infinite]
  val locked: Boolean = this[TableUserWallet.locked]

  return RawUserWallet(
    identifier,
    coins,
    multiplier,
    infinite,
    locked
  )
}

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawUserWallet(): RawUserWallet {
  return this.toRawUserWallet(
    this[TableUserWallet.identifier]
  )
}