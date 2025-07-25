package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToString
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property statistics
 * @property wallet
 * @property name
 * @property profession
 * @property locale
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawUser(
  val identifier: UUID,
  val statistics: RawUserStatistics,
  val wallet: RawUserWallet,
  val name: String,
  val profession: String,
  val locale: String
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("RawUser")
      .add("identifier", this.identifier)
      .addNested("statistics", this.statistics)
      .addNested("wallet", this.wallet)
      .add("name", this.name)
      .add("profession", this.profession)
      .add("locale", this.locale)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}
