package me.kvdpxne.dtm.user

import java.time.LocalDateTime
import java.util.UUID
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.shared.ancillary.Auditable
import me.kvdpxne.dtm.uid.Uid
import me.kvdpxne.dtm.wallet.Wallet

/**
 * @param statistics
 * @param wallet
 * @param creationDate
 * @param lastModificationDate
 * @param identifier
 *
 * @since 0.1.0
 */
open class OfflineUser(
  // @formatter:off
           val statistics          : UserStatistics,
           val wallet              : Wallet,
               identifier          : UUID           = UUID.randomUUID()
  // @formatter:on
) : AbstractIdentifiable<UUID>(identifier) {

  /**
   * @since 0.1.0
   */
  val selectedProfession: Profession?
    get() = null
}