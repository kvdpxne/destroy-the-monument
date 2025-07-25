package me.kvdpxne.dtm.data.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.tables.UserTable
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.main.validateUser
import org.jetbrains.exposed.sql.ResultRow

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawUser(): Pair<RawUser, ValidationResult> {
  val statistics: Pair<RawUserStatistics, ValidationResult> =
    this[UserTable.statisticsIdentifier].let { statisticsIdentifier: UUID ->
      this.toRawUserStatistics(statisticsIdentifier)
    }

  val wallet: Pair<RawUserWallet, ValidationResult> =
    this[UserTable.walletIdentifier].let { walletIdentifier: UUID ->
      this.toRawUserWallet(walletIdentifier)
    }

  val identifier: UUID = this[UserTable.identifier]
  val name: String = this[UserTable.name]
  val professionName: String = this[UserTable.profession]
  val locale: String = this[UserTable.locale] ?: "en_us" // TODO usunąć stała

  val result: ValidationResult = validateUser(
    identifier,
    null,
    null,
    name,
    professionName,
    locale
  ).combine(statistics.second, wallet.second)

  val user = RawUser(
    identifier,
    statistics.first,
    wallet.first,
    name,
    professionName,
    locale
  )

  return Pair(user, result)
}

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawUser(
  statistics: RawUserStatistics?,
  wallet: RawUserWallet?
): Pair<RawUser, ValidationResult> {
  if (null == statistics || null == wallet) {
    throw IllegalStateException("")
  }

  val identifier: UUID = this[UserTable.identifier]
  val name: String = this[UserTable.name]
  val professionName: String = this[UserTable.profession]
  val locale: String = this[UserTable.locale] ?: "en_us" // TODO usunąć stała

  val result: ValidationResult = validateUser(
    identifier,
    null,
    null,
    name,
    professionName,
    locale,
    true
  )

  val user = RawUser(
    identifier,
    statistics,
    wallet,
    name,
    professionName,
    locale
  )

  return Pair(user, result)
}