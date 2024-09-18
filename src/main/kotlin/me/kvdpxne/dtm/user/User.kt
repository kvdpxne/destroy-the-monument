package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.ancillary.Identifiable
import me.kvdpxne.dtm.shared.ancillary.Nameable
import me.kvdpxne.dtm.wallet.Wallet

/**
 * @since 0.1.0
 */
interface User : Identifiable<String>, Nameable {

  /**
   * @since 0.1.0
   */
  override val identifier: String

  /**
   * @since 0.1.0
   */
  override val name: String

  /**
   * @since 0.1.0
   */
  override val displayName: String

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