package me.kvdpxne.dtm.data.raw

import java.util.UUID
import me.kvdpxne.dtm.data.shared.Raw
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * @property identifier
 * @property coins
 * @property multiplier
 * @property infinite
 * @property locked
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
data class RawUserWallet(
  val identifier: UUID,
  val coins: Long,
  val multiplier: Float,
  val infinite: Boolean,
  val locked: Boolean,
) : Raw, StylishToStringProvider {

  /**
   * @since 0.1.0
   */
  override fun toStylishString(): StylishToStringBuilder {
    return StylishToStringBuilder
      .begin("RawUserWallet")
      .add("identifier", this.identifier)
      .add("coins", this.coins)
      .add("multiplier", this.multiplier)
      .add("infinite", this.infinite)
      .add("locked", this.locked)
  }

  /**
   * @since 0.1.0
   */
  override fun toString(): String {
    return this.toStylishString().packed()
  }
}
