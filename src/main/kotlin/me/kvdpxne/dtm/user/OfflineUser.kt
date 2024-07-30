package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable

/**
 * @param statistics
 * @param wallet
 * @param identifier
 *
 * @since 0.1.0
 */
open class OfflineUser(
  // @formatter:off
  val statistics: UserStatistics,
  val wallet    : Wallet,
      identifier: UUID = UUID.randomUUID()
  // @formatter:on
) : AbstractIdentifiable<UUID>(identifier) {

  /**
   * @since 0.1.0
   */
  val selectedProfession: Profession?
    get() = null
}