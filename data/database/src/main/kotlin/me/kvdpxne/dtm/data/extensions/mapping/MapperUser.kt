package me.kvdpxne.dtm.data.extensions.mapping

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.tables.TableUser
import me.kvdpxne.dtm.data.validation.context.isUserDisplayNameValid
import me.kvdpxne.dtm.data.validation.context.isUserLocalizationValid
import me.kvdpxne.dtm.data.validation.context.isUserNameValid
import org.jetbrains.exposed.sql.ResultRow

/**
 * @since 0.1.0
 */
internal fun ResultRow.toRawUser(): RawUser {
  // Primary key of the table.
  val identifier: UUID = this[TableUser.identifier]

  // Foreign keys referencing other tables.
  val statisticsIdentifier: UUID = this[TableUser.statisticsIdentifier]
  val walletIdentifier: UUID = this[TableUser.walletIdentifier]

  // Mapped objects from the result row values.
  val statistics: RawUserStatistics = this.toRawUserStatistics(statisticsIdentifier)
  val wallet: RawUserWallet = this.toRawUserWallet(walletIdentifier)

  // The remaining values of the row, which are not referenced.
  val name: String = this[TableUser.name]
  check(isUserNameValid(name)) {
    """
      The database returned an invalid user name "$name" for a user with the
      identifier "$identifier".
    """.trimIndent()
  }

  val displayName: String? = this[TableUser.displayName]
  check(isUserDisplayNameValid(displayName)) {
    """
        The database returned an invalid displayed user name "$displayName" for
        a user with the identifier "$identifier".
      """.trimIndent()
  }

  val professionName: String = this[TableUser.profession]

  val locale: String = this[TableUser.locale] ?: "en_us" // TODO usunąć stała
  if ("en_us" != locale) {
    check(isUserLocalizationValid(locale)) {
      ""
    }
  }

  return RawUser(
    identifier,
    statistics,
    wallet,
    name,
    displayName,
    professionName,
    locale
  )
}