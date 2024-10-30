package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.Identifiable
import me.kvdpxne.dtm.shared.Nameable
import me.kvdpxne.dtm.wallet.Wallet

/**
 * @since 0.1.0
 */
interface User : Identifiable<UUID>, Nameable {

  /**
   * @since 0.1.0
   */
  val statistics: UserStatistics

  /**
   * @since 0.1.0
   */
  val wallet: Wallet

  /**
   * @since 0.1.0
   */
  val currentProfession: Profession

  /**
   * @since 0.1.0
   */
  fun asLocalUser(): LocalUser
}